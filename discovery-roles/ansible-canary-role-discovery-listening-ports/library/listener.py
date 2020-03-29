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
module: listner
short_description: take output from netstat to json
version_added: "2.4"
author: Sean S <ssulliva@redhat.com>
description:
  - This module takes the input from of output from the shell command:
  'netstat -tulpn | awk "{if (NR>2) {print}}"'
  It then takes the that data and converts it into a JSON format.
'''

EXAMPLES = '''
- name: Netstat output
  shell: 'netstat -tulpn | awk "{if (NR>2) {print}}"'
  register: netstat

- name: Convert Netstat output to Json
  listener:
    netstat: "{{netstat.stdout_lines | to_json}}"
  register: result
'''

RETURN = '''{
        "changed": false,
        "listeners": [
            {
                "foreign_address": "0.0.0.0",
                "foreign_port": "*",
                "local_address": "192.168.124.1",
                "local_port": "53",
                "pid": "1414",
                "process": "dnsmasq",
                "protocol": "tcp",
                "state": "LISTEN"
            },
            {
                "foreign_address": "0.0.0.0",
                "foreign_port": "*",
                "local_address": "127.0.0.1",
                "local_port": "631",
                "pid": "2202",
                "process": "cupsd",
                "protocol": "tcp",
                "state": "LISTEN"
            },
        ]
    }

'''

try:
    import json
except ImportError:
    import simplejson as json
from ansible.module_utils.basic import *


def parseNetstat(netstatDatastr):

    entryList = []

    for i in range (len (netstatDatastr)):

        netstatData = netstatDatastr[i].encode('utf-8').split()

        tmpDict ={}

        #LocalIP/Port
        tmpLocal = netstatData[3].rsplit(':',1)
        #ForeignIP/Port
        tmpForeign = netstatData[4].rsplit(':',1)

        tmpDict["protocol"] = netstatData[0].replace('\"', '')
        tmpDict["local_address"] = tmpLocal[0]
        tmpDict["local_port"] = tmpLocal[1]
        tmpDict["foreign_address"] = tmpForeign[0]
        tmpDict["foreign_port"] = tmpForeign[1]

        if str(netstatData[5])[:2].isalpha():

            tmpDict["state"] = netstatData[5]

            if "/" in netstatData[6]:
                tmpPID = netstatData[6].split('/')
                tmpDict["pid"] = tmpPID[0]
                tmpDict["process"] = tmpPID[1]
            else:
                tmpDict["pid"] = netstatData[6]
                tmpDict["process"] = netstatData[6]
        else:
            tmpDict["state"] = ""
            if "/" in netstatData[5]:
                tmpPID = netstatData[5].split('/')
                tmpDict["pid"] = tmpPID[0]
                tmpDict["process"] = tmpPID[1]
            else:
                tmpDict["pid"] = netstatData[5]
                tmpDict["process"] = netstatData[5]

        entryList.append(tmpDict)

    return entryList


def main():
    fields = {
        "netstat": { "required": True, "type": "list" }
    }

    module = AnsibleModule(argument_spec=fields)

    data = parseNetstat(module.params['netstat'])
    output = {}
    output['listeners'] = data
    results = {"ansible_facts":output}
    module.exit_json(**results)


if __name__ == '__main__':
    main()
