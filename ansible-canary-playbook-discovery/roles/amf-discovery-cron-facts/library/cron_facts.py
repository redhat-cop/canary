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
    output_raw_configs:
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
    - Andrew J. Huffman (@ahuffman)
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
    output_raw_configs: False
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
import os
from os.path import isfile, isdir, join
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

    def get_cron_allow():
        allow = dict()
        allow['path'] = '/etc/cron.allow'
        allow['users'] = list()
        try:
            cron_allow = open('/etc/cron.allow', 'r')
            for line in cron_allow:
                user = line.replace('\n', '')
                allow['users'].append(user)
            cron_allow.close()
        except:
            pass
        return allow

    def get_cron_deny():
        deny = dict()
        deny['path'] = '/etc/cron.deny'
        deny['users'] = list()
        try:
            cron_deny = open('/etc/cron.deny', 'r')
            for line in cron_deny:
                user = line.replace('\n', '')
                deny['users'].append(user)
            cron_deny.close()
        except:
            pass
        return deny

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
                cron_paths += [join(dir, filename) for filename in os.listdir(dir) if isfile(join(dir, filename))]
                # keep digging
                cron_dirs += [join(dir, filename) for filename in os.listdir(dir) if isdir(join(dir, filename))]
            except:
                pass
        return cron_paths

    def get_cron_data(cron_paths):
        # Output data
        cron_data = list()
        # Regex for parsing data
        variable_re = re.compile(r'^([a-zA-Z0-9_-]*)[ \t]*=[ \t]*(.*)$')
        comment_re = re.compile(r'^#+')
        shebang_re = re.compile(r'^(#!){1}(.*)$')
        schedule_re = re.compile(r'^([0-9a-zA-Z\*\-\,\/]+)[ \t]+([0-9a-zA-Z\*\-\,\/]+)[ \t]+([0-9a-zA-Z\*\-\,\/]+)[ \t]+([0-9a-zA-Z\*\-\,\/]+)[ \t]+([0-9a-zA-Z\*\-\,\/]+)[ \t]+([A-Za-z0-9\-\_]*)[ \t]*(.*)$')

        # work on each file that was found
        for cron in cron_paths:
            job = dict()
            job['path'] = cron

            if params['output_raw_configs']:
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
                    if params['output_raw_configs']:
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
                            variable['name'] = variable_re.search(line).group(1)
                            variable['value'] = variable_re.search(line).group(2)
                            job['data']['variables'].append(variable)

                        # Capture script shell type
                        if shebang_re.search(line) and line != '' and line != None:
                            job['data']['shell'] = shebang_re.search(line).group(2)

                        # Capture cron schedules:
                        ##  don't try if a shell is set on the file, because it's a script at that point
                        if schedule_re.search(line) and line != '' and line != None and job['data']['shell'] == '':
                            sched['minute'] = schedule_re.search(line).group(1)
                            sched['hour'] = schedule_re.search(line).group(2)
                            sched['day_of_month'] = schedule_re.search(line).group(3)
                            sched['month'] = schedule_re.search(line).group(4)
                            sched['day_of_week'] = schedule_re.search(line).group(5)
                            # optional user field in some implementations
                            if schedule_re.search(line).group(6):
                                sched['user'] = schedule_re.search(line).group(6)
                            sched['command'] = schedule_re.search(line).group(7)
                            job['data']['schedules'].append(sched)
                config.close()

            except:
                pass

            # append each parsed file
            cron_data.append(job)
        return cron_data

    # Do work
    cron_allow = get_cron_allow()
    cron_deny = get_cron_deny()
    cron_paths = get_cron_files()
    cron_data = get_cron_data(cron_paths)

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
