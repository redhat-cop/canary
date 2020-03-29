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
    - John Westcott IV (@john-westcott-iv)
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

from ansible_collections.ansible_fact.os_facts.plugins.module_utils.fact_gatherer import FactGatherer
import re

class ProcessGatherer(FactGatherer):
    def get_processes(self, command):
        re_header = re.compile(r'^USER+.*')
        self.raw_processes = []
#### How do we call run_command????
        rc, stdout, stderr = self.run_command(command, check_rc=True)
        for line in stdout.split('\n'):
            self.raw_output.append(line)
            if len(line) > 0 and re_header.search(line) is None:
                self.raw_processes.append(line)

    def parse_process_data(self):
        re_ps = re.compile(r'^(?P<user>[\w\+\-\_\$]+)\s+(?P<pid>[0-9]+)\s+(?P<cpu>[0-9\.]+)\s+(?P<mem>[0-9\.]+)\s+(?P<vsz>[0-9]+)\s+(?P<rss>[0-9]+)\s+(?P<tty>[a-zA-Z0-9\?\/]+)\s+(?P<stat>[DIRSTtWXZ\<NLsl\+]+)\s+(?P<start>[A-Za-z0-9\:]+)\s+(?P<time>[0-9\:\.]+)\s+(?P<command>.*)$')

        self.parsed_processes = []
        for proc in self.raw_processes:
            with open("/tmp/john", "w") as f:
                f.write(proc)

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
                self.parsed_processes.append(process)

    def doDefault(self):
        # Do work
        self.get_processes(['/bin/ps', 'auxww'])

        # Build output
        processes = dict()
        if self.output_ps_stdout_lines:
            processes['ps_stdout_lines'] = self.raw_output

        if self.output_parsed_processes:
            self.parse_process_data()
            processes['total_running_processes'] = len(self.parsed_processes)
            processes['processes'] = self.parsed_processes
        self.exit_json(**{'ansible_facts': {'running_processes': processes}})

    def __init__(self, argument_spec, **kwargs):
        # Call the parent constructor
        super(ProcessGatherer, self).__init__(argument_spec=argument_spec, **kwargs)
        # Extract the module params into class variables
        self.output_parsed_processes = self.params['output_parsed_processes']
        self.output_ps_stdout_lines = self.params['output_ps_stdout_lines']
        # Set additional class variables
        self.raw_output = []
        self.parsed_processes = []



def main():
    module = ProcessGatherer(
        dict(
            output_ps_stdout_lines=dict(type='bool', default=False, required=False),
            output_parsed_processes=dict(type='bool', default=True, required=False),
        ),
        supports_check_mode=True,
    )

    module.main()


if __name__ == '__main__':
    main()
