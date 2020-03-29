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
module: crontab
short_description: take output from crontab to json
version_added: "2.4"
author: Sean S <ssulliva@redhat.com>
description:
  - This module takes the input from of output from the shell command:
  'crontab -l'
  It then takes the that data and converts it into a JSON format.
'''

EXAMPLES = '''
- name: Crontab view
  shell: 'crontab -l'
  register: crontab


- name: Convert crontab output to Json
  crontab_tojson:
    crontab: "{{crontab.stdout_lines | to_json}}"
  register: result
'''

RETURN = '''{
    "msg": {
        "changed": false,
        "crontab": [
            {
                "command": "/home/root/backup.sh",
                "day": "1,10,20,30",
                "hour": "17",
                "minute": "59",
                "month": "*",
                "weekday": "*"
            }
        ]
    }
}


'''

try:
    import json
except ImportError:
    import simplejson as json
import re
from ansible.module_utils.basic import *


entryList = []

def parseCronTab(crontabDataList):
    entryList = []
    re_string = r"\d|\*"
    for i in range(len(crontabDataList)):
        cronTabRow = crontabDataList[i].encode('utf-8')
        if len(cronTabRow) > 1:
            if re.match(re_string,cronTabRow[0]):
                data = cronTabRow.split()
                tmpDict = {}
                tmpDict["minute"] = data[0]
                tmpDict["hour"] = data[1]
                tmpDict["day"] = data[2]
                tmpDict["month"] = data[3]
                tmpDict["weekday"] = data[4]
                tmpDict["user"] = data[5]
                tmpDict["command"] = data[6] +" "+ data[7]
                entryList.append(tmpDict)

    return entryList

def parserow(crontabDatastr):
    #Split the string into a list
    crontabData = crontabDatastr.split()
    #Define Dicts for 8 entries
    tmpDict ={}

    tmpDict["minute"] = crontabData[0]
    tmpDict["hour"] = crontabData[1]
    tmpDict["day"] = crontabData[2]
    tmpDict["month"] = crontabData[3]
    tmpDict["weekday"] = crontabData[4]
    tmpDict["command"] = crontabData[5]
    #append all items to the list
    entryList.append(tmpDict)

def createOutputMap(crontab, cronfiles, varSpool):
    output = {'cron_jobs': {'cronfiles': cronfiles , 'crontab': crontab, 'var_spool': varSpool } }
    return output


def main():

    fields = {
        "crontab": { "required": True, "type": "list" },
        "cronfiles": {"required": True, "type": "list"},
        "varSpool" : { "required": True, "type": "list"}
    }

    module = AnsibleModule(argument_spec=fields)

    cronTabOutput = parseCronTab(module.params['crontab'])
    output = createOutputMap(cronTabOutput, module.params['cronfiles'], module.params['varSpool'])
    results = {"ansible_facts":output}
    module.exit_json(**results)


if __name__ == '__main__':
    main()
