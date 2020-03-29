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
module: repolist
short_description: take output from yum repolist to json
version_added: "2.4"
author: Sean S <ssulliva@redhat.com>
description:
  - This module takes the input in for the from of output from the shell command:
  'yum repolist |  awk "{if (NR>3) {print}}" | head -n -1 '
  Optional this module will also work with DNF using hte following command for input
  'dnf repolist |  awk "{if (NR>3) {print}}"'
  It then takes the that data and converts it into a JSON format.
'''

EXAMPLES = '''
- name: get repolist
  shell: 'yum repolist all |  head -n -1 '
  register: repoList

- name: Convert repolist output to Json
  repolist:
    repolist: "{{repoList.stdout_lines | to_json}}"
  register: result
'''

RETURN = '''{
        "changed": false,
        "repos": [
            {
                "repo-id": "google-chrome",
                "repo-name": "google-chrome"
                "repo-status": "enabled"
            }
        ]
    }
'''

try:
    import json
except:
    import simplejson as json

import re

from ansible.module_utils.basic import *

def parseRepoList(reposDataStr):
    entryList = []

    repoIdRegex = re.compile("repo id")
    rIdLine = list(filter(repoIdRegex.search, reposDataStr))

    startIndex = reposDataStr.index(rIdLine[0])

    for i in range (startIndex+1, len(reposDataStr)):
        repoRow = reposDataStr[i].encode('utf-8').replace('\"', '').replace('[', '').replace(']', '')
        if len(repoRow) > 1:
            reposData = repoRow.split()
            tmpDict = {}

            repo_name = ""
            tmpDict["repo_id"] = reposData[0]
            tmpDict["repo_name"] = repo_name
            tmpDict["repo_status"] = ""

            for i in range(1,len(reposData)):

                if "enabled" in reposData[i]:
                    enabled = reposData[i].replace(":","")
                    tmpDict["repo_status"] = enabled
                    break
                elif reposData[i] == "disabled":
                    tmpDict["repo_status"] = reposData[i]
                    break
                else:
                    repo_name = repo_name +" "+ reposData[i]

            tmpDict["repo_name"] = repo_name
        entryList.append(tmpDict)
    return entryList

def main():
    fields = {
        "repolist": { "required": True, "type": "list" }
    }

    module = AnsibleModule(argument_spec=fields)

    data = parseRepoList(module.params['repolist'])

    output = {"repo_list" : data}

    module.exit_json(meta=output)


if __name__ == '__main__':
    main()
