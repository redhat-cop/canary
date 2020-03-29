#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: scan_cron
short_description: Collects cron job facts
version_added: "2.8"
description:
    - "Collects cron job facts from a system."
    - "The module can display both parsed and raw cron configurations which is useful when some cron jobs are scripts and others are true schedules."
    - "The display of either raw configurations or parsed configurations can be limited via the module parameters."
options:
    output_effective_configs:
        description:
            - Whether or not to output raw configuration lines (excluding comments) from the scanned cron files
        default: True
        required: False
    output_parsed_configs:
        description:
            - Whether or not to output parsed data from the scanned cron files
        default: True
        required: False
author:
    - "Andrew J. Huffman (@ahuffman)"
    - "John Westcott IV (@john-westcott-iv)"
'''

EXAMPLES = '''
# Collect all cron data (defaults)
- name: "Collect all cron data"
  scan_cron:

# Collect only raw configurations (minus comments)
- name: "Collect raw cron configs"
  scan_cron:
    output_parsed_configs: False

# Collect only parsed configuration data
#     This is only useful if you have no scripting logic in the cron files (i.e. if's, do untils, etc.)
- name: "Collect parsed cron data"
  scan_cron:
    output_effective_configs: False
'''

RETURN = '''
# From a default Fedora configuration
cron:
  all_scanned_files:
    - /etc/crontab
    - /etc/cron.hourly/0anacron
    - /etc/cron.weekly/98-zfs-fuse-scrub
    - /var/spool/cron/root
    - /etc/cron.d/0hourly
    - /etc/cron.d/raid-check
  allow:
    path: /etc/cron.allow
    users: []
  deny:
    path: /etc/cron.deny
    users: []
  files:
    - configuration:
        - SHELL=/bin/bash
        - PATH=/sbin:/bin:/usr/sbin:/usr/bin
        - MAILTO=root
      data:
        schedules: []
        variables:
          - name: SHELL
            value: /bin/bash
          - name: PATH
            value: /sbin:/bin:/usr/sbin:/usr/bin
          - name: MAILTO
            value: root
      path: /etc/crontab
    - configuration:
        - '#!/usr/bin/sh'
        - if test -r /var/spool/anacron/cron.daily; then
        - '    day=`cat /var/spool/anacron/cron.daily`'
        - fi
        - if [ `date +%Y%m%d` = "$day" ]; then
        - '    exit 0'
        - fi
        - online=1
        - for psupply in AC ADP0 ; do
        - '    sysfile="/sys/class/power_supply/$psupply/online"'
        - '    if [ -f $sysfile ] ; then'
        - '        if [ `cat $sysfile 2>/dev/null`x = 1x ]; then'
        - '            online=1'
        - '            break'
        - '        else'
        - '            online=0'
        - '        fi'
        - '    fi'
        - done
        - if [ $online = 0 ]; then
        - '    exit 0'
        - fi
        - /usr/sbin/anacron -s
      data:
        schedules: []
        shell: /usr/bin/sh
        variables:
          - name: online
            value: '1'
      path: /etc/cron.hourly/0anacron
    - configuration:
        - '#!/usr/bin/bash'
        - '[ -f /etc/sysconfig/zfs-fuse ] || exit 0'
        - . /etc/sysconfig/zfs-fuse
        - '[ "$ZFS_WEEKLY_SCRUB" != "yes" ] && exit 0'
        - zpool=/usr/sbin/zpool
        - pools=`$zpool list -H | cut -f1`
        - if [ "$pools" != "" ] ; then
        - '    echo Found these pools: $pools'
        - '    for pool in $pools; do'
        - '    echo "Starting scrub of pool $pool"'
        - '    $zpool scrub $pool'
        - '    done'
        - '    echo "ZFS Fuse automatic scrub start done.  Use ''$zpool status'' to see progress."'
        - fi
      data:
        schedules: []
        shell: /usr/bin/bash
        variables:
          - name: zpool
            value: /usr/sbin/zpool
          - name: pools
            value: '`$zpool list -H | cut -f1`'
      path: /etc/cron.weekly/98-zfs-fuse-scrub
    - configuration: []
      data:
        schedules: []
        variables: []
      path: /var/spool/cron/root
    - configuration:
        - SHELL=/bin/bash
        - PATH=/sbin:/bin:/usr/sbin:/usr/bin
        - MAILTO=root
        - 01 * * * * root run-parts /etc/cron.hourly
      data:
        schedules:
          - command: run-parts /etc/cron.hourly
            day_of_month: '*'
            day_of_week: '*'
            hour: '*'
            minute: '01'
            month: '*'
            user: root
        variables:
          - name: SHELL
            value: /bin/bash
          - name: PATH
            value: /sbin:/bin:/usr/sbin:/usr/bin
          - name: MAILTO
            value: root
      path: /etc/cron.d/0hourly
    - configuration:
        - 0 1 * * Sun root /usr/sbin/raid-check
      data:
        schedules:
          - command: /usr/sbin/raid-check
            day_of_month: '*'
            day_of_week: Sun
            hour: '1'
            minute: '0'
            month: '*'
            user: root
        variables: []
      path: /etc/cron.d/raid-check
'''

from ansible.module_utils.basic import AnsibleModule
# from ansible_collections.ansible_fact.os_facts.plugins.module_utils.fact_gatherer import FactGatherer
import re
import stat
from pwd import getpwuid
from grp import getgrgid
from os.path import isfile, isdir, join
from os import stat, listdir

def main():
    module_args = dict(
        # output actual config lines as list
        output_effective_configs = dict(
            type='bool',
            default=True,
            required=False
        ),
        # strips comments from effective configs
        output_strip_comments = dict(
            type='bool',
            default=False,
            required=False
        ),
        # structured data
        output_parsed_configs = dict(
            type='bool',
            default=True,
            required=False
        ),
        # ignore default cron file search paths and use this list instead
        cron_files = dict(
            type='list',
            default=[],
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

    def get_perms(filename):
        file_perms = dict()
        try:
            file_stats = stat(filename)
            try:
                file_perms['owner'] = getpwuid(file_stats.st_uid).pw_name
            except KeyError as e:
                file_perms['owner'] = file_stats.st_uid
            try:
                file_perms['group'] = getgrgid(file_stats.st_gid).gr_name
            except KeyError as e:
                file_perms['group'] = file_stats.st_gid
            try:
                file_perms['mode'] = oct(file_stats.st_mode)[-4:]
            except:
                file_perms['mode'] = ''
        except:
            # add a message we couldn't read the file later
            file_perms['owner'] = None
            file_perms['group'] = None
            file_perms['mode'] = None
            pass
        return file_perms

    def get_cron_allow_deny(allow_or_deny, filename = None):
        result = dict()
        if filename == None or filename == '':
            path = "/etc/cron.{}".format(allow_or_deny)
        else:
            # to handle passing allow/deny files with module param list
            path = filename

        try:
            # grab file permissions
            perms = get_perms(path)
            result['users'] = list()
            result['owner'] = perms['owner']
            result['group'] = perms['group']
            result['mode'] = perms['mode']
            file = open(path, 'rt')
            for line in file:
                user = line.replace('\n', '')
                result['users'].append(user)
            file.close()
            result['owner'] = perms['owner']
            result['group'] = perms['group']
            result['mode'] = perms['mode']
            result['path'] = path
        except:
            result = dict(
                path = path,
                info = "File does not exist"
            )
            pass
        return result

    def get_cron_files():
        # standard cron locations for cron file discovery
        cron_paths = [
            "/etc/crontab"
        ]
        cron_dirs = [
            "/etc/cron.hourly",
            "/etc/cron.daily",
            "/etc/cron.weekly",
            "/etc/cron.monthly",
            "/var/spool/cron",
            "/etc/cron.d"
        ]

        # Look for files in cron directories and append to cron_paths
        for dir in cron_dirs:
            try:
                cron_paths += [join(dir, filename) for filename in listdir(dir) if isfile(join(dir, filename))]
                # keep digging
                cron_dirs += [join(dir, filename) for filename in listdir(dir) if isdir(join(dir, filename))]
            except:
                pass
        return cron_paths

    def get_cron_data(cron_paths):
        # Output data
        cron_data = list()
        # Regexes for parsing data
        ## for capturing key/value pairs in schedules
        variable_re = re.compile(r'^(?P<name>[a-zA-Z0-9_-]*)[ \t]*=[ \t]*(?P<value>.*)$')
        ## for capturing/filtering comment lines
        comment_re = re.compile(r'^#+')
        ## for capturing script shell line
        shebang_re = re.compile(r'^(?P<shebang>#!){1}(?P<shell>.*)$')
        ## normal cron schedules
        schedule_re = re.compile(r'^(?P<minute>[0-9a-zA-Z\*\-\,\/]+)[ \t]+(?P<hour>[0-9a-zA-Z\*\-\,\/]+)[ \t]+(?P<day_of_month>[0-9a-zA-Z\*\-\,\/]+)[ \t]+(?P<month>[0-9a-zA-Z\*\-\,\/]+)[ \t]+(?P<day_of_week>[0-9a-zA-Z\*\-\,\/]+)[ \t]+(?P<user>[A-Za-z0-9\-\_]*)[ \t]*(?P<command>.*)$')
        ## timeframe style schedule
        timeframe_schedule_re = re.compile(r'^@(?P<timeframe>[a-zA-Z]+)[\s\t]+(?P<command>[A-Za-z0-9\-\_]*)[\s\t]*(?P<comments>.*)$')

        # work on each file that was found
        for cron in cron_paths:
            job = dict()
            job['path'] = cron

            if params['output_effective_configs']:
                job['configuration'] = list()

            if params['output_parsed_configs']:
                job['data'] = dict()
                job['data']['variables'] = list()
                job['data']['schedules'] = list()
                job['data']['shell'] = ''

            # make sure we have permission to open the files
            try:
                config = open(cron, 'r')
                for l in config:
                    line = l.replace('\n', '').replace('\t', '    ')
                    # raw configuration output
                    if params['output_effective_configs']:
                        # Not a comment line
                        if comment_re.search(line) is None and line != '' and line != None:
                            job['configuration'].append(line)

                        # Shebang line
                        elif shebang_re.search(line) and line != '' and line != None:
                            job['configuration'].append(line)

                    # parsed data output
                    if params['output_parsed_configs']:
                        variable = dict()
                        sched = dict()

                        # Capture script variables
                        if variable_re.search(line) and line != '' and line != None:
                            variable['name'] = variable_re.search(line).group('name')
                            variable['value'] = variable_re.search(line).group('value')
                            job['data']['variables'].append(variable)

                        # Capture script shell type
                        if shebang_re.search(line) and line != '' and line != None:
                            job['data']['shell'] = shebang_re.search(line).group('shell')

                        # Capture cron schedules:
                        ##  don't try if a shell is set on the file, because it's a script at that point
                        if schedule_re.search(line) and line != '' and line != None and job['data']['shell'] == '':
                            sched['minute'] = schedule_re.search(line).group('minute')
                            sched['hour'] = schedule_re.search(line).group('hour')
                            sched['day_of_month'] = schedule_re.search(line).group('day_of_month')
                            sched['month'] = schedule_re.search(line).group('month')
                            sched['day_of_week'] = schedule_re.search(line).group('day_of_week')
                            # optional user field in some implementations
                            if schedule_re.search(line).group('user'):
                                sched['user'] = schedule_re.search(line).group('user')
                            sched['command'] = schedule_re.search(line).group('command')
                            job['data']['schedules'].append(sched)
                        # if not normal schedule, not an empty line and not a cron script file
                        elif timeframe_schedule_re.search(line) != None and line != '' and line != None and job['data']['shell'] == '':
                            sched['timeframe'] = timeframe_schedule_re.group('timeframe')
                            sched['command'] = timeframe_schedule_re.group('command')
                            job['data']['schedules'].append(sched)

                config.close()

            except:
                pass

            # append each parsed file
            cron_data.append(job)
        return cron_data

    # Do work
    cron_paths = get_cron_files()
    cron_data = get_cron_data(cron_paths)
    cron_allow = get_cron_allow_deny('allow')
    cron_deny = get_cron_allow_deny('deny')
    # After main work
    ## Make sure our allow/deny files get added to scanned paths list


    # Build output
    cron = dict()
    cron['allow'] = cron_allow
    cron['deny'] = cron_deny
    cron['all_scanned_files'] = cron_paths
    cron['files'] = cron_data
    result = {'ansible_facts': {'cron': cron}}

    module.exit_json(**result)


if __name__ == '__main__':
    main()
