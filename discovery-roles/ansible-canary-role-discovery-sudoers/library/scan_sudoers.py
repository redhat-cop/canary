#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: "scan_sudoers"
short_description: "Parses the /etc/sudoers and /etc/sudoers.d/* files."
version_added: "2.7"
author:
    - "Andrew J. Huffman (@ahuffman)"
description:
    - "This module is designed to collect information from C(/etc/sudoers).  The #include (files) and #includedir (directories) will be dynamically calculated and all included files will be parsed."
    - "This module is compatible with Linux and Unix systems."
    - "You will need to run the playbook as a privileged user or a user with appropriate privilege escalation"
options:
    output_raw_configs:
        description:
            - Whether or not to output raw configuration lines (excluding comments) from the scanned sudoers files
        default: True
        required: False
    output_parsed_configs:
        description:
            - Whether or not to output parsed data from the scanned sudoers files
        default: True
        required: False
'''

EXAMPLES = '''
- name: "Scan sudoers files - output everything"
  scan_sudoers:

- name: "Scan sudoers files - output raw configuration lines only"
  scan_sudoers:
    output_parsed_configs: False

- name: "Scan sudoers files - output parsed configurations only"
  scan_sudoers:
    output_raw_configs: False

- name: "Scan sudoers files - output only included files and paths (minimal output)"
  scan_sudoers:
    output_raw_configs: False
    output_parsed_configs: False
'''

RETURN = '''
sudoers:
    description: "List of parsed sudoers data and included sudoers data"
    returned: "success"
    type: "list"
    sample:
      ansible_facts:
        sudoers:
          all_scanned_files:
            - /etc/sudoers.d/group1
            - /etc/sudoers.d/group2
            - /etc/sudoers
          sudoers_files:
            - aliases:
                cmnd_alias:
                host_alias:
                runas_alias:
                user_alias:
              configuration:
                - 'Host_Alias        SOMEHOSTS = server1, server2'
                - ...
                - '#includedir /etc/sudoers.d'
              defaults:
                - '!visiblepw'
                - env_reset
                - secure_path:
                    - /usr/local/sbin
                    - /usr/local/bin
                    - /usr/sbin
                    - /usr/bin
                    - /sbin
                    - /bin
                - env_keep:
                    - COLORS
                    - DISPLAY
                    - ...
                - ...
              include_directories:
                - /etc/sudoers.d
              include_files:
                - /etc/sudoers.d/file1
                - /etc/sudoers.d/file2
                - /tmp/some/file
                - ...
              path: /etc/sudoers
              user_specifications:
                - commands:
                    - ALL
                  hosts:
                    - ALL
                  operators:
                    - ALL
                  tags:
                    - NOPASSWD
                  users:
                    - '%wheel'
                - defaults:
                    - '!requiretty'
                  type: user
                  users:
                    - STAFF
                    - INTERNS
            - aliases:
                ...
'''

from ansible.module_utils.basic import AnsibleModule
import os
from os.path import isfile, join
import re

def main():
    module_args = dict(
        output_raw_configs=dict(
            type='bool',
            default=True,
            required=False
        ),
        output_parsed_configs=dict(
            type='bool',
            default=True,
            required=False
        )
    )

    result = dict(
        changed=False,
        original_message='',
        message=''
    )

    module = AnsibleModule(
        argument_spec=module_args,
        supports_check_mode=True
    )
    params = module.params

    def get_includes(path):
        ## Get includes
        sudoers_file = open(path, 'r')
        includes = dict()
        includes['include_files'] = list()
        include_dir = ""
        includes['include_directories'] = list()

        # Regex for "#includedir" and "#include" sudoers options
        includedir_re = re.compile(r'(^#includedir)+\s+(.*$)')
        include_re = re.compile(r'(^#include)+\s+(.*$)')

        for l in sudoers_file:
            line = l.replace('\n', '').replace('\t', '    ')
            # Search for '#includedir'
            if includedir_re.search(line):
                include_dir = includedir_re.search(line).group(2)
            # Search for '#include'
            if include_re.search(line):
                includes['include_files'].append(include_re.search(line).group(2))

        if include_dir:
            # build multi-file output
            includes['include_directories'].append(include_dir)
            # Get list of all included sudoers files
            includes['include_files'] += [join(include_dir, filename) for filename in os.listdir(include_dir) if isfile(join(include_dir, filename))]
        elif not includes['include_files']:
            includes.pop('include_files')

        if not includes['include_directories']:
            includes.pop('include_directories')

        sudoers_file.close()
        return includes

    def get_user_specs(line, path):
        user_spec = dict()
        user_spec_re =  re.compile(r'(^\S+,{1}\s*\S+|^\S+)\s*(\S+,{1}\s*|\S+){1}\s*={1}\s*(\({1}(.*)\){1})*\s*(ROLE\s*=\s*(\S+)|TYPE\s*=\s*(\S+))*\s*(ROLE\s*=\s*(\S+)|TYPE\s*=\s*(\S+))*\s*(PRIVS\s*=\s*(\S+)|LIMITPRIVS\s*=\s*(\S+))*\s*(PRIVS\s*=\s*(\S+)|LIMITPRIVS\s*=\s*(\S+))*\s*(\S+:{1})*\s*(.*$)')
        default_override_re = re.compile(r'(Defaults){1}([@:!>]){1}((\s*\S+,{1})+\s*\S+|\S+)\s*(.*$)')
        spec_fields = user_spec_re.search(line)
        if user_spec_re.search(line):
            user_spec['users'] = list()
            user_spec['hosts'] = list()
            user_spec['operators'] = list()
            user_spec['selinux_role'] = ""
            user_spec['selinux_type'] = ""
            user_spec['solaris_privs'] = ""
            user_spec['solaris_limitprivs'] = ""
            user_spec['tags'] = list()
            user_spec['commands'] = list()
            # users
            users = spec_fields.group(1).split(',')
            for user in users:
                if user != '' and user != None:
                    user_spec['users'].append(user.lstrip())
            # hosts
            hosts = spec_fields.group(2).split(',')
            for host in hosts:
                if host != '' and host != None:
                    user_spec['hosts'].append(host.lstrip())
            # operators - optional
            if spec_fields.group(4):
                operators = spec_fields.group(4).split(',')
                for op in operators:
                    if op != '' and op != None:
                        user_spec['operators'].append(op.lstrip())
            # SELinux - optional
            if spec_fields.group(5) or spec_fields.group(8):
              ## TYPE
                type_re = re.compile(r'(^TYPE){1}\s*={1}\s*')
                if spec_fields.group(5):
                    if type_re.search(spec_fields.group(5)):
                        if type_re.search(spec_fields.group(5)).group(1) == 'TYPE':
                            user_spec['selinux_type'] = spec_fields.group(7)
                if spec_fields.group(8):
                    if type_re.search(spec_fields.group(8)):
                        if type_re.search(spec_fields.group(8)).group(1) == 'TYPE':
                            user_spec['selinux_type'] = spec_fields.group(10)
              ## ROLE
                role_re = re.compile(r'(^ROLE){1}\s*={1}\s*')
                if spec_fields.group(5):
                    if role_re.search(spec_fields.group(5)):
                        if role_re.search(spec_fields.group(5)).group(1) == 'ROLE':
                            user_spec['selinux_role'] = spec_fields.group(6)
                if spec_fields.group(8):
                    if role_re.search(spec_fields.group(8)):
                        if role_re.search(spec_fields.group(8)).group(1) == 'ROLE':
                            user_spec['selinux_role'] = spec_fields.group(9)
            # Solaris - optional
            if spec_fields.group(11) or spec_fields.group(14):
              ## PRIVS
                privs_re = re.compile(r'(^PRIVS){1}\s*={1}\s*')
                if spec_fields.group(11):
                    if privs_re.search(spec_fields.group(11)):
                        if privs_re.search(spec_fields.group(11)).group(1) == 'PRIVS':
                            user_spec['solaris_privs'] = spec_fields.group(12)
                if spec_fields.group(14):
                    if privs_re.search(spec_fields.group(14)):
                        if privs_re.search(spec_fields.group(14)).group(1) == 'PRIVS':
                            user_spec['solaris_privs'] = spec_fields.group(17)
              ## LIMITPRIVS
                limitprivs_re = re.compile(r'(^LIMITPRIVS){1}\s*={1}\s*')
                if spec_fields.group(11):
                    if limitprivs_re.search(spec_fields.group(11)):
                        if limitprivs_re.search(spec_fields.group(11)).group(1) == 'LIMITPRIVS':
                            user_spec['solaris_limitprivs'] = spec_fields.group(13)
                if spec_fields.group(14):
                    if limitprivs_re.search(spec_fields.group(14)):
                        if limitprivs_re.search(spec_fields.group(14)).group(1) == 'LIMITPRIVS':
                            user_spec['solaris_limitprivs'] = spec_fields.group(16)
            # tags - optional
            if spec_fields.group(17):
                tags = spec_fields.group(17).split(':')
                for tag in tags:
                    if tag != '' and tag != None:
                        user_spec['tags'].append(tag)
            # commands
            commands = spec_fields.group(18).split(',')
            for command in commands:
                if command != '' and command != None:
                    user_spec['commands'].append(command.lstrip())
            # Cleanup unused output
            if user_spec['selinux_role'] == '':
                user_spec.pop('selinux_role')
            if user_spec['selinux_type'] == '':
                user_spec.pop('selinux_type')
            if user_spec['solaris_privs'] == '':
                user_spec.pop('solaris_privs')
            if user_spec['solaris_limitprivs'] == '':
                user_spec.pop('solaris_limitprivs')
            if not user_spec['users']:
                user_spec.pop('users')
            if not user_spec['hosts']:
                user_spec.pop('hosts')
            if not user_spec['operators']:
                user_spec.pop('operators')
            if not user_spec['tags']:
                user_spec.pop('tags')
            if not user_spec['commands']:
                user_spec.pop('commands')
        else:
            if default_override_re.search(line):
                default_override = default_override_re.search(line)
                # type
                if default_override.group(2) == '@':
                    user_spec['type'] = 'host'
                    user_spec['hosts'] = list()
                    hosts = default_override.group(3).split(',')
                    for host in hosts:
                        if host != '' and host != None:
                            user_spec['hosts'].append(host.lstrip())
                elif default_override.group(2) == ':':
                    user_spec['type'] = 'user'
                    user_spec['users'] = list()
                    users = default_override.group(3).split(',')
                    for user in users:
                        if user != '' and user != None:
                            user_spec['users'].append(user.lstrip())
                elif default_override.group(2) == '!':
                    user_spec['type'] = 'command'
                    user_spec['commands'] = list()
                    commands = default_override.group(3).split(',')
                    for command in commands:
                        if command != '' and command != None:
                            user_spec['commands'].append(command.lstrip(optionals))
                elif default_override.group(2) == '>':
                    user_spec['type'] = 'runas'
                    user_spec['operators'] = list()
                    operators = default_override.group(3).split(',')
                    for op in operators:
                        if op != '' and op != None:
                            user_spec['operators'].append(op.lstrip())
                user_spec['defaults'] = list()
                defaults = default_override.group(5).split(',')
                for default in defaults:
                    if default != '' and default != None:
                        user_spec['defaults'].append(default.lstrip())
        return user_spec

    def get_config_lines(path):
        # Read sudoers file
        all_lines = open(path, 'r')
        # Initialize empty return dict
        sudoer_file = dict()
        # Initialize aliases vars
        sudoer_aliases = dict()
        user_aliases = list()
        runas_aliases = list()
        host_aliases = list()
        command_aliases = list()
        user_specifications = list()
        # Raw config lines output
        config_lines = list()

        # Regex for Parsers
        comment_re = re.compile(r'^#+')
        include_re = re.compile(r'^#include')
        defaults_re = re.compile(r'^(Defaults)+\s+(.*$)')
        cmnd_alias_re = re.compile(r'(^Cmnd_Alias)+\s+(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+)\s*(\:)*(.*)*$')
        host_alias_re = re.compile(r'(^Host_Alias)+\s+(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+)\s*(\:)*(.*)*$')
        runas_alias_re = re.compile(r'(^Runas_Alias)+\s+(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+)\s*(\:)*(.*)*$')
        user_alias_re = re.compile(r'(^User_Alias)+\s+(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+)\s*(\:)*(.*)*$')

        # Defaults Parsing vars
        config_defaults = list()
        env_keep_opts = list()

        # Get includes from file
        includes = get_includes(path)
        # if we have included files add them to the list
        try:
            sudoer_file['include_files'] = includes['include_files']
        except:
            pass
        try:
            sudoer_file['include_directories'] = includes['include_directories']
        except:
            pass
        # Work on each line of sudoers file
        for l in all_lines:
            line = l.replace('\n', '').replace('\t', '    ') #cleaning up chars we don't want
            # only output raw config lines if we ask for them
            if params['output_raw_configs']:
                # All raw (non-comment) config lines out
                if comment_re.search(line) is None and line != '' and line != None:
                    config_lines.append(line)
                if include_re.search(line):
                    config_lines.append(line)

            # only output parsed configs if we ask for them
            if params['output_parsed_configs']:
                # Parser for defaults
                if defaults_re.search(line):
                    defaults_config_line = defaults_re.search(line).group(2)
                    defaults_env_keep_re = re.compile(r'^(env_keep)+((\s\=)|(\s\+\=))+(\s)+(.*$)')
                    defaults_sec_path_re = re.compile(r'^(secure_path)+(\s)+(\=)+(\s)+(.*$)')
                    # Break up multi-line defaults config lines into single config options
                    if defaults_env_keep_re.search(defaults_config_line):
                        defaults_multi = defaults_env_keep_re.search(defaults_config_line).group(6).split()
                        # env_keep default options
                        for i in defaults_multi:
                            env_keep_opts.append(i.replace('"', ''))
                    # build secure path dict and append to defaults list
                    elif defaults_sec_path_re.search(defaults_config_line):
                        secure_paths = defaults_sec_path_re.search(defaults_config_line).group(5).split(':')
                        config_defaults.append({'secure_path': secure_paths})
                    # single defaults option case
                    else:
                        config_defaults.append(defaults_config_line)
                # Aliases:
                # Parser for Command Alias
                if cmnd_alias_re.search(line):
                    if cmnd_alias_re.search(line).group(5) == ':':
                        # We have a multi line alias
                        cmnd_multi_line_aliases = line.split(':')
                        # Process each alias
                        ca_multi_re = re.compile(r'(^Cmnd_Alias)*\s*(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+).*$')
                        for ca in cmnd_multi_line_aliases:
                            ca_fields = ca_multi_re.search(ca)
                            cmnds_name = ca_fields.group(2)
                            ca_cmnds = list()
                            ca_cmnds_split = ca_fields.group(3).split(',')
                            for cmnd in ca_cmnds_split:
                                ca_cmnds.append(cmnd.lstrip())
                            cmnd_alias_formatted = {'name': cmnds_name, 'commands': ca_cmnds}
                            command_aliases.append(cmnd_alias_formatted)
                    else:
                        command_name = cmnd_alias_re.search(line).group(2)
                        commands = list()
                        for i in cmnd_alias_re.search(line).group(3).split(','):
                            # Append a space free item to the list
                            commands.append(i.replace(' ', ''))
                        # Build command alias dict
                        cmnd_alias_formatted = {'name': command_name, 'commands': commands}
                        command_aliases.append(cmnd_alias_formatted)

                # Parser for Host Alias
                if host_alias_re.search(line):
                    if host_alias_re.search(line).group(5) == ':':
                        # We have a multi line alias
                        host_multi_line_aliases = line.split(':')
                        # Process each alias
                        ha_multi_re = re.compile(r'(^Host_Alias)*\s*(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+).*$')
                        for ha in host_multi_line_aliases:
                            ha_fields = ha_multi_re.search(ha)
                            hosts_name = ha_fields.group(2)
                            ha_hosts = list()
                            ha_hosts_split = ha_fields.group(3).split(',')
                            for host in ha_hosts_split:
                                ha_hosts.append(host.lstrip())
                            host_alias_formatted = {'name': hosts_name, 'hosts': ha_hosts}
                            host_aliases.append(host_alias_formatted)
                    else:
                        host_name = host_alias_re.search(line).group(2)
                        hosts = list()
                        for i in host_alias_re.search(line).group(3).split(','):
                            # Append a space free item to the list
                            hosts.append(i.replace(' ', ''))
                        # Build command alias dict
                        host_alias_formatted = {'name': host_name, 'hosts': hosts}
                        host_aliases.append(host_alias_formatted)

                # Parser for RunAs Alias
                if runas_alias_re.search(line):
                    if runas_alias_re.search(line).group(5) == ':':
                        # We have a multi line alias
                        runas_multi_line_aliases = line.split(':')
                        # Process each alias
                        ra_multi_re = re.compile(r'(^Runas_Alias)*\s*(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+).*$')
                        for ra in user_multi_line_aliases:
                            ra_fields = ra_multi_re.search(ra)
                            runas_name = ra_fields.group(2)
                            ra_users = list()
                            ra_users_split = ra_fields.group(3).split(',')
                            for user in ra_users_split:
                                ra_users.append(user.lstrip())
                            runas_alias_formatted = {'name': runas_name, 'users': ra_users}
                            runas_aliases.append(runas_alias_formatted)
                    else:
                        runas_name = runas_alias_re.search(line).group(2)
                        ra_users = list()
                        for i in runas_alias_re.search(line).group(3).split(','):
                            # Append a space free item to the list
                            ra_users.append(i.replace(' ', ''))
                        # Build command alias dict
                        runas_alias_formatted = {'name': runas_name, 'users': ra_users}
                        runas_aliases.append(runas_alias_formatted)

                # Parser for User Alias
                if user_alias_re.search(line):
                    if user_alias_re.search(line).group(5) == ':':
                        # We have a multi line alias
                        user_multi_line_aliases = line.split(':')
                        # Process each alias
                        ua_multi_re = re.compile(r'(^User_Alias)*\s*(\S+)+\s*\={1}\s*((\S+,{1}\s*)+\S+|\S+).*$')
                        for ua in user_multi_line_aliases:
                            ua_fields = ua_multi_re.search(ua)
                            users_name = ua_fields.group(2)
                            ua_users = list()
                            ua_users_split = ua_fields.group(3).split(',')
                            for user in ua_users_split:
                                ua_users.append(user.lstrip())
                            user_alias_formatted = {'name': users_name, 'users': ua_users}
                            user_aliases.append(user_alias_formatted)
                    else:
                        users_name = user_alias_re.search(line).group(2)
                        ua_users = list()
                        for i in user_alias_re.search(line).group(3).split(','):
                            # Append a space free item to the list
                            ua_users.append(i.lstrip())
                        # Build command alias dict
                        user_alias_formatted = {'name': users_name, 'users': ua_users}
                        user_aliases.append(user_alias_formatted)

                # Parser for user_specs
                if not user_alias_re.search(line) and not runas_alias_re.search(line) and \
                   not host_alias_re.search(line) and not cmnd_alias_re.search(line) and \
                   not include_re.search(line) and not comment_re.search(line) and \
                   not defaults_re.search(line) and line != '' and \
                   line != None:
                    user_spec = get_user_specs(line, path)
                    user_specifications.append(user_spec)
        # Build the sudoer file's dict output
        sudoer_file['path'] = path

        # only output raw configs if we ask for it
        if params['output_raw_configs']:
            sudoer_file['configuration'] = config_lines

        if params['output_parsed_configs']:
            # Build defaults env_keep dict and append to the rest of the config_defaults list
            if env_keep_opts:
                config_defaults.append({'env_keep': env_keep_opts})
            if config_defaults:
                sudoer_file['defaults'] = config_defaults
            # Build aliases output dictionary
            sudoer_aliases = {'user_alias': user_aliases, 'runas_alias': runas_aliases, 'cmnd_alias': command_aliases, 'host_alias': host_aliases}
            # cleanup unused outputs
            if not sudoer_aliases['user_alias']:
                sudoer_aliases.pop('user_alias')
            if not sudoer_aliases['runas_alias']:
                sudoer_aliases.pop('runas_alias')
            if not sudoer_aliases['cmnd_alias']:
                sudoer_aliases.pop('cmnd_alias')
            if not sudoer_aliases['host_alias']:
                sudoer_aliases.pop('host_alias')
            if sudoer_aliases:
                sudoer_file['aliases'] = sudoer_aliases
            sudoer_file['user_specifications'] = user_specifications
        # done working on the file
        all_lines.close()
        return sudoer_file

    def get_sudoers_configs(path):
        sudoers = dict()
        include_files = list()

        # Get parsed values from default sudoers file
        sudoers['sudoers_files'] = list()
        default = get_config_lines(path)
        if default:
            sudoers['sudoers_files'].append(default)
            try:
                include_files += default['include_files']
            except:
                pass
        # Capture each included sudoer file
        for file in include_files:
            include_file = get_config_lines(file)
            if include_file:
                sudoers['sudoers_files'].append(include_file)
            # append even more included files as we parse deeper
            try:
                include_files += include_file['include_files']
            except:
                pass
        # return back everything that was included off of the default sudoers file
        include_files.append(default_sudoers)
        sudoers['all_scanned_files'] = include_files
        return sudoers


    default_sudoers = '/etc/sudoers'
    sudoers = get_sudoers_configs(default_sudoers)
    result = {'ansible_facts': {'sudoers': sudoers}}

    module.exit_json(**result)


if __name__ == '__main__':
    main()
