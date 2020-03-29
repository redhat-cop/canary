#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: netstat_facts
short_description: Collect information from netstat
version_added: "2.8"
description:
    - "Collects results of netstat."
options:
    output_parsed_configs:
        description:
            - Parse the results
        default: True
        required: False
author:
    - Andrew J. Huffman (@ahuffman)
    - John Westcott IV (@john-westcott-iv)
'''

EXAMPLES = '''
- name: "Collect all netstat data"
  listen_ports_facts:

- name: "Dont parse output, only show raw data"
  listen_ports_facts:
    output_parsed_configs: False
'''

RETURN = '''
# TODO
'''

from ansible_collections.ansible_fact.os_facts.plugins.module_utils.fact_gatherer import FactGatherer
from os.path import isfile
from os import access, X_OK
import re

class NetstatGatherer(FactGatherer):
    #def doLinux(self):
    #    command = self.findCommand('netstat')
    #    try:
    #        rc, stdout, stderr = self.run_command([command, '-plunt'])
    #    except Exception as e:
    #        self.fail_json(msg="Failed to run {}: {}".format(command, e))
#
#        self.exit_json(**{ 'ansible_facts': {'cron': cron } })


    def runAndParseLSOF(self, lsof_args):
        command = self.findCommand('lsof')
        try:
            rc, stdout, stderr = self.run_command([command, lsof_args])
        except Exception as e:
            self.warn(msg="Failed to run {}: {}".format(command, e))
            if rc != 0:
                self.warn("Failed to run {} successfully: {}".format(command, rc))
            self.exit_json(**dict(skipped=True, msg="Problem running {}. Return code from command: {}".format(command, rc)))
        if not self.parse_configs:
            self.exit_json(**{'lsof_stdout_lines': stdout.split('\n')})

        re_listen_ports = re.compile(r'^(?P<name>[^\s]+)\s+(?P<pid>[0-9]+)\s+(?P<user>[^\s]+)\s+(?P<fd>\d+[^\s]+)\s+(?P<type>[^\s]+)\s+(?P<dev>[^\s]+)\s+(?P<size>[^\s]+)\s+(?P<protocol>[^\s]+)\s+(?P<address>[^:]+):(?P<port>[^\s]+)( \((?P<state>[^\(]+)\)){0,1}$')

        tcp_listen_ports = list()
        udp_listen_ports = list()
        for line in stdout.split('\n'):
            line_match = re_listen_ports.search(line)
            if line_match != None:
                port = {
                    'name': line_match.group('name'),
                    'pid': line_match.group('pid'),
                    'user': line_match.group('user'),
                    'fd': line_match.group('fd'),
                    'type': line_match.group('type'),
                    'dev': line_match.group('dev'),
                    'size': line_match.group('size'),
                    'protocol': line_match.group('protocol'),
                    'address': line_match.group('address'),
                    'port': line_match.group('port'),
                    'state': line_match.group('state'),
                }

                if port['protocol'] == 'UDP' or (port['protocol'] == 'TCP' and port['state'] == 'LISTEN'):
                    if port['address'] == '*':
                        port['address'] = '0.0.0.0'
                if port['protocol'] == 'UDP':
                    udp_listen_ports.append(port)
                elif port['protocol'] == 'TCP':
                    tcp_listen_ports.append(port)

        self.exit_json(**{ 'ansible_facts': {'tcp_listen': tcp_listen_ports, 'udp_listen': udp_listen_ports}})

    def doDarwin(self):
        self.runAndParseLSOF('+c0 -n -P -i')

    def doAIX(self):
        self.runAndParseLSOF('+c0 -i -n -P')

    def __init__(self, argument_spec, **kwargs):
        # Call the parent constructor
        super(NetstatGatherer, self).__init__(argument_spec=argument_spec, **kwargs)
        # Extract the module params into class variables
        self.parse_configs = self.params.get('output_parsed_configs')
        # Set additional class variables



def main():
    module = NetstatGatherer(
        dict(
            output_parsed_configs=dict(type='bool', default=True, required=False),
        ),
        supports_check_mode=True
    )
    module.main()


if __name__ == '__main__':
    main()
