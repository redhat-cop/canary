#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: scan_user_group
short_description: "Parses the local users and groups on a system"
version_added: "2.7"
author:
    - "Andrew J. Huffman (@ahuffman)"
    - "John Westcott IV (@john-westcott-iv)"
description:
    - "This module is designed to collect information from C(/etc/passwd), C(/etc/shadow), C(/etc/group), and C(/etc/gshadow) files and returns a set of Ansible facts based on the collected data."
    - "This module is compatible with Linux and Unix systems."
options:
    passwd_path:
        description:
            - "To override where this module collects its C(/etc/passwd) data from on a system"
        default: "/etc/passwd"
        required: false
        version_added: "2.7"
    shadow_path:
        description:
            - "To override where this module collects its C(/etc/shadow) data from on a system"
        default: "/etc/shadow"
        required: false
        version_added: "2.7"
    group_path:
        description:
            - "To override where this module collects its C(/etc/group) data from on a system"
        default: "/etc/group"
        required: false
        version_added: "2.7"
    gshadow_path:
        description:
            - "To override where this module collects its C(/etc/gshadow) data from on a system"
        default: "/etc/gshadow"
        required: false
        version_added: "2.7"
'''

EXAMPLES = '''
- name: "Scan Users and Groups with defaults"
  scan_user_group:

- name: "Scan Users and Groups with path overrides"
  scan_user_group:
    passwd_path: "/etc/passwd2"
    shadow_path: "/etc/shadow-"
    group_path: "/etc/group2"
    gshadow_path: "/etc/gshadow2"

- name: "Parse backed up shadow file"
  scan_user_group:
    shadow_path: "/etc/shadow-"
'''

RETURN = '''
local_users:
    description: "List of parsed local user data.  This is a merged list of data from /etc/passwd and /etc/shadow by default."
    returned: "success"
    type: "list"
    sample: "[{'account_expiration': '', 'comment': 'Account comment', 'encrypted_password': '$AdfaRdak/.serSrA', 'gid': '24', 'home': '/home/myuser', 'last_pw_change': '17621', 'max_pw_age': '', 'min_pw_age': '', 'pw_inactive_days': '', 'pw_warning_days': '', 'reserved': '', 'shadow': True, 'shell': '/sbin/nologin', 'uid': '24', 'user': 'ahuffman'}]"
local_groups:
    description: "List of parsed local group data.  This is a merged list of data from /etc/group and /etc/gshadow by default."
    returned: "success"
    type: "list"
    sample: "[{'administrators': [], 'encrypted_password': '', 'gid': '0', 'group': 'root', 'gshadow': True, 'members': []}]"
'''

from ansible_collections.ansible_fact.os_facts.plugins.module_utils.fact_gatherer import FactGatherer
from os.path import isfile

class UserGroupGatherer(FactGatherer):
    def read_lines(self, file_name):
        lines = []
        try:
            with open(file_name, 'rt') as f:
                for a_line in f:
                    lines.append(self.remove_comment(a_line.replace("\n", ""), '#'))
        except Exception as e:
            self.fail_json(msg="Failed to read {} : {}".format(file_name, e))

        return lines

    def get_passwd(self):
        # conditional for OS type and set path if diverges from Linux
        users = list()
        if not isfile(self.passwd_path):
            return users
        line_number = 0
        for a_line in self.read_lines(self.passwd_path):
            line_number = line_number + 1
            user = dict()
            fields = a_line.split(':')
            if len(fields) == 7:
                user['user'] = fields[0]
                if fields[1] == self.shadow_character:
                    user['shadow'] = True
                else:
                    user['shadow'] = False
                    user['encrypted_password'] = fields[1]
                user['uid'] = fields[2]
                user['gid'] = fields[3]
                user['comment'] = fields[4]
                user['home'] = fields[5]
                user['shell'] = fields[6]
                users.append(user)
            elif len(fields) > 1 or fields[0] != '':
                self.fail_json(msg="Failed to parse line {} in {}".format(line_number, self.passwd_path))
        return users

    def get_shadow(self):
        # conditional for OS type and set path if diverges from Linux
        susers = list()
        if not isfile(self.shadow_path):
            return susers
        line_number = 0
        for a_line in self.read_lines(self.shadow_path):
            line_number = line_number + 1
            user = dict()
            fields = a_line.split(':')
            if len(fields) == 9:
                user['user'] = fields[0]
                user['encrypted_password'] = fields[1]
                user['last_pw_change'] = fields[2]
                user['min_pw_age'] = fields[3]
                user['max_pw_age'] = fields[4]
                user['pw_warning_days'] = fields[5]
                user['pw_inactive_days'] = fields[6]
                user['account_expiration'] = fields[7]
                user['reserved'] = fields[8]
                susers.append(user)
            elif len(fields) > 1 or fields[0] != '':
                self.fail_json(msg="Failed to parse line {} in {}".format(line_number, self.passwd_path))

        return susers

    def get_group(self):
        groups = list()
        if not isfile(self.group_path):
            return groups
        line_number = 0
        for a_line in self.read_lines(self.group_path):
            line_number = line_number + 1
            # conditional for OS type and set path if diverges from Linux

            group = dict()
            fields = a_line.split(':')
            if len(fields) == 4:
                group['group'] = fields[0]
                if fields[1] == self.shadow_character:
                    group['gshadow'] = True
                else:
                    group['encrypted_password'] = fields[1]
                    group['gshadow'] = False
                group['gid'] = fields[2]
                if fields[3] != '':
                    members = fields[3].split(',')
                else:
                    members = list()
                group['members'] = members
                groups.append(group)
            # An empty string will be split into ['']
            elif len(fields) > 1 or fields[0] != '':
                self.fail_json(msg="Failed to parse line {} in {}: {}".format(line_number, self.group_path, a_line))

        return groups

    def get_gshadow(self):
        sgroups = list()
        if not isfile(self.gshadow_path):
            return sgroups
        line_number = 0
        for a_line in self.read_lines(self.gshadow_path):
            line_number = line_number + 1
            sgroup = dict()
            fields = a_line.split(':')
            if len(fields) == 4:
                sgroup['group'] = fields[0]
                sgroup['encrypted_password'] = fields[1]
                if fields[2] != '':
                    administrators = fields[2].split(',')
                else:
                    administrators = list()
                sgroup['administrators'] = administrators
                if fields[3] != '':
                    members = fields[3].split(',')
                else:
                    members = list()
                sgroup['members'] = members
                sgroups.append(sgroup)
            elif len(fields) > 1 or fields[0] != '':
                self.fail_json(msg="Failed to parse line {} in {}".format(line_number, self.gshadow_path))

        return sgroups

    def merge_data(self, starting_dict, adding_dict, merge_key):
        # takes list of group dictionaries (groups) and
        #   list of shadow group dictionaries (gshadow)
        merged_items = list()
        for item in starting_dict:
            for item_2 in adding_dict:
              if item_2[merge_key] == item[merge_key]:
                  # NOTE: This will technically actually change item in starting_dict
                  item.update(item_2)
            merged_items.append(item)
        return merged_items

    def doDarwin(self):
        self.warn("Darwin is not fully implemented, we don\'t read the OS X user databases".format(self.shadow_character))
        self.shadow_character = '*'
        self.doDefault()

    def doDefault(self):
        groups = self.merge_data(self.get_group(), self.get_gshadow(), 'group')
        users = self.merge_data(self.get_passwd(), self.get_shadow(), 'user')
        result = {'ansible_facts': { 'local_users': users, 'local_groups': groups }}

        self.exit_json(**result)

    def __init__(self, argument_spec, **kwargs):
        # Call the parent constructor
        super(UserGroupGatherer, self).__init__(argument_spec=argument_spec, **kwargs)
        # Extract the module params into class variables
        self.passwd_path = self.params.get('passwd_path')
        self.shadow_path = self.params.get('shadow_path')
        self.group_path = self.params.get('group_path')
        self.gshadow_path = self.params.get('gshadow_path')
        # Set additional class variables
        self.shadow_character = 'x'

def main():
    module = UserGroupGatherer(
        dict(
            passwd_path=dict(
                type='str',
                required=False,
                default='/etc/passwd'
            ),
            shadow_path=dict(
                type='str',
                required=False,
                default='/etc/shadow'
            ),
            group_path=dict(
                type='str',
                required=False,
                default='/etc/group'
            ),
            gshadow_path=dict(
                type='str',
                required=False,
                default='/etc/gshadow'
            )
        ),
        supports_check_mode=True
    )

    module.main()



if __name__ == '__main__':
    main()
