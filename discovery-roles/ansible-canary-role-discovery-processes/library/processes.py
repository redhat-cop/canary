# !/usr/bin/python
# -*- coding: utf-8 -*-

#
# This file is part of Ansible
#
# Ansible is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# Ansible is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with Ansible.  If not, see <http://www.gnu.org/licenses/>.

ANSIBLE_METADATA = {'metadata_version': '1.0',
                    'status': ['stableinterface'],
                    'supported_by': 'core'}

DOCUMENTATION = '''
---
module: processes
short_description: take output from ps aux to json
version_added: "2.4"
author: Sean S <ssulliva@redhat.com>
description:
  - This module takes the input in for the from of output from the shell command:
  ' ps aux '
  It then takes the that data and converts it into a JSON format.
'''

EXAMPLES = '''
- name: PS Aux output
  shell: 'ps aux '
  register: ps_aux

- name: Convert ps_aux output to Json
  listener:
    processes: "{{ps_aux.stdout_lines | to_json}}"
  register: result
'''

RETURN = '''{
        "changed": false,
        "processes": [
            {
                "COMMAND": "[pdflush]",
                "CPU_Perc": "0.0",
                "MEM_Perc": "0.0",
                "PID": "15629",
                "STAT": "S",
                "TTY": "?",
                "USER": "root"
            },
            {
                "COMMAND": "[pdflush]",
                "CPU_Perc": "0.0",
                "MEM_Perc": "0.0",
                "PID": "16456",
                "STAT": "S",
                "TTY": "?",
                "USER": "root"
            }
        ]
    }
}

'''

try:
    import json
except ImportError:
    import simplejson as json

from ansible.module_utils.basic import *
from itertools import chain

entryList = []


def parserow(processDatastr):
    processData = processDatastr.split()
    tmpDict = {}


    tmpDict["USER"] = processData[0]
    tmpDict["PID"] = processData[1]
    tmpDict["CPU_Perc"] = processData[2]
    tmpDict["MEM_Perc"] = processData[3]
    tmpDict["TTY"] = processData[6]
    tmpDict["STAT"] = processData[7]

    command = ""
    for i in range(10,len(processData)):
        command = command + " " + processData[i]
    tmpDict["COMMAND"] = command

    entryList.append(tmpDict)


def main():
    module = AnsibleModule(
        argument_spec=dict(
            processes=dict(required=True, type='str'),
        )
    )

    processes = (module.params['processes'])

    # Process each line of the input
    for i in range(len(json.loads(processes))):
        parserow((json.loads(processes)[i]).encode('utf-8'))
    # Exit module with JSON output
    output = {}
    output['processes'] = entryList
    results = {"ansible_facts":output}
    module.exit_json(**results)


if __name__ == '__main__':
    main()
