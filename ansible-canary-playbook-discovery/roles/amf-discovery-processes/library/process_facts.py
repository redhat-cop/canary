#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: scan_processes
short_description: Collects currently running processes on a system
version_added: "2.8"
description:
    - "Collects the currently running processes on a system at the time the module is run."
    - "This module presents the currently running proceses as ansible_facts"
output_ps_stdout_lines:
    description:
        - Whether or not to output the collected standard out lines from the 'ps auxww' command
    default: False
    required: False
output_parsed_processes:
    description:
        - Whether or not to output parsed data from the 'ps auxww' command.
    default: True
    required: False
author:
    - Andrew J. Huffman (@ahuffman)
'''

EXAMPLES = '''
# Collect running processes and output parsed data
- name: "Collect current running processes"
  scan_processes:

# Collect only standard out lines from the ps auxww command
- name: "Collect process command output"
  scan_processes:
    output_ps_stdout_lines: True
    output_parsed_processes: False

# Collect both parsed process data and 'ps auxww' command standard out
- name: "Collect all process data"
  scan_processes:
    output_ps_stdout_lines: True
'''

RETURN = '''
running_processes:
    processes:
      - command: /usr/lib/systemd/systemd --switched-root --system --deserialize 33
        cpu_percentage: '0.0'
        memory_percentage: '0.0'
        pid: '1'
        resident_size: '5036'
        start: Jul08
        stat: Ss
        teletype: '?'
        time: '3:32'
        user: root
      ...
    ps_stdout_lines:
      - root         1  0.0  0.0 171628  5056 ?        Ss   Jul08   3:32 /usr/lib/systemd/systemd --switched-root --system --deserialize 33
      ...
    total_running_processes: 359
'''

from ansible.module_utils.basic import AnsibleModule
import os, re, subprocess
from os.path import isfile, isdir, join

def main():
    module_args = dict(
        output_ps_stdout_lines=dict(
            type='bool',
            default=False,
            required=False
        ),
        output_parsed_processes=dict(
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

    def get_processes():
        re_header = re.compile(r'^USER+.*')
        proc_stats = dict()
        procs = list()
        count = 0
        running = subprocess.check_output(["ps auxww"], universal_newlines=True, shell=True)
        for l in running.split('\n'):
            if len(l) > 0 and re_header.search(l) is None:
                procs.append(l.replace('\n', '').replace('\t', '    '))
                count += 1
        proc_stats['stdout'] = procs
        proc_stats['total_running_processes'] = count
        return proc_stats

    def parse_process_data(procs):
        re_ps = re.compile(r'^(?P<user>[\w\+\-\_\$\d]+)\s+(?P<pid>[0-9]+)\s+(?P<cpu>[0-9\.]+)\s+(?P<mem>[0-9\.]+)\s+(?P<vsz>[0-9]+)\s+(?P<rss>[0-9]+)\s+(?P<tty>[a-zA-Z0-9\?\/\-]+)\s+(?P<stat>[ADIRSTtWXZ\<NLsl\+]+)\s+(?P<start>[\w\:\d]+\s?[\d]{2})\s+(?P<time>[0-9\:]+)\s+(?P<command>.*)$')
        processes = list()

        for proc in procs:
            process = dict()
            if re_ps.search(proc):
                process['user'] = re_ps.search(proc).group('user')
                process['pid'] = re_ps.search(proc).group('pid')
                process['cpu_percentage'] = re_ps.search(proc).group('cpu')
                process['memory_percentage'] = re_ps.search(proc).group('mem')
                process['virtual_memory_size'] = re_ps.search(proc).group('vsz')
                process['resident_size'] = re_ps.search(proc).group('rss')
                process['teletype'] = re_ps.search(proc).group('tty')
                process['stat'] = re_ps.search(proc).group('stat')
                process['start'] = re_ps.search(proc).group('start')
                process['time'] = re_ps.search(proc).group('time')
                process['command'] = re_ps.search(proc).group('command')
                processes.append(process)
        return processes

    # Do work
    raw_procs = get_processes()
    if params['output_parsed_processes']:
        proc_data = parse_process_data(raw_procs['stdout'])

    # Build output
    processes = dict()
    if params['output_ps_stdout_lines']:
        processes['ps_stdout_lines'] = raw_procs['stdout']
    if params['output_parsed_processes']:
        processes['total_running_processes'] = raw_procs['total_running_processes']
        processes['processes'] = proc_data
    result = {'ansible_facts': {'running_processes': processes}}

    module.exit_json(**result)


if __name__ == '__main__':
    main()
