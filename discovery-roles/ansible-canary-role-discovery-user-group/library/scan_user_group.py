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

from ansible.module_utils.basic import AnsibleModule
import os

def main():
    module_args = dict(
        passwd_path=dict(type='str', required=False, default='/etc/passwd'),
        shadow_path=dict(type='str', required=False, default='/etc/shadow'),
        group_path=dict(type='str', required=False, default='/etc/group'),
        gshadow_path=dict(type='str', required=False, default='/etc/gshadow')
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

    def get_passwd(path):
        # conditional for OS type and set path if diverges from Linux
        passwd_file = open(path, 'rt')
        users = list()
        for u in passwd_file:
            user = dict()
            field = u.replace('\n', '').split(':')
            if len(field) > 0:
                user['user'] = field[0]
                if field[1] == 'x':
                    user['shadow'] = True
                else:
                    user['shadow'] = False
                    user['encrypted_password'] = field[1]
                user['uid'] = field[2]
                user['gid'] = field[3]
                user['comment'] = field[4]
                user['home'] = field[5]
                user['shell'] = field[6]
            users.append(user)
        passwd_file.close()
        return users

    def get_shadow(path):
        # conditional for OS type and set path if diverges from Linux
        shadow_file = open(path, 'rt')
        susers = list()
        for u in shadow_file:
            user = dict()
            field = u.replace('\n', '').split(':')
            if len(field) > 0:
                user['user'] = field[0]
                user['encrypted_password'] = field[1]
                user['last_pw_change'] = field[2]
                user['min_pw_age'] = field[3]
                user['max_pw_age'] = field[4]
                user['pw_warning_days'] = field[5]
                user['pw_inactive_days'] = field[6]
                user['account_expiration'] = field[7]
                user['reserved'] = field[8]
            susers.append(user)
        shadow_file.close()
        return susers

    def get_group(path):
        # conditional for OS type and set path if diverges from Linux
        group_file = open(path, 'rt')
        groups = list()
        for g in group_file:
            group = dict()
            field = g.replace('\n', '').split(':')
            if len(field) > 0:
                group['group'] = field[0]
                if field[1] == 'x' or field[1] == '!':
                    group['gshadow'] = True
                else:
                    group['encrypted_password'] = field[1]
                    group['gshadow'] = False
                group['gid'] = field[2]
                if field[3] != '':
                    members = field[3].split(',')
                else:
                    members = list()
                group['members'] = members
            groups.append(group)
        group_file.close()
        return groups

    def get_gshadow(path):
        # conditional for OS type and set path if diverges from Linux
        gshadow_file = open(path, 'rt')
        sgroups = list()
        for g in gshadow_file:
            sgroup = dict()
            field = g.replace('\n', '').split(':')
            if len(field) > 0:
                sgroup['group'] = field[0]
                sgroup['encrypted_password'] = field[1]
                if field[2] != '':
                    administrators = field[2].split(',')
                else:
                    administrators = list()
                sgroup['administrators'] = administrators
                if field[3] != '':
                    members = field[3].split(',')
                else:
                    members = list()
                sgroup['members'] = members
            sgroups.append(sgroup)
        gshadow_file.close()
        return sgroups

    def merge_group_data(groups, gshadow):
        # takes list of group dictionaries (groups) and
        #   list of shadow group dictionaries (gshadow)
        groups_merged = list()
        for g in groups:
            key = g['group']
            for s in gshadow:
                if s['group'] == key:
                    s.update(g)
                    groups_merged.append(s)
        return groups_merged

    def merge_user_data(users, shadow):
        # takes list of user dictionaries (users) and
        #   list of shadow user dictionaries (shadow)
        users_merged = list()
        for u in users:
            key = u['user']
            for s in shadow:
                if s['user'] == key:
                    s.update(u)
                    users_merged.append(s)
        return users_merged

    groups = merge_group_data(get_group(module.params['group_path']), get_gshadow(module.params['gshadow_path']))
    users = merge_user_data(get_passwd(module.params['passwd_path']),get_shadow(module.params['shadow_path']))

    output = {'local_users': users, 'local_groups': groups}
    result = {'ansible_facts': output}

    module.exit_json(**result)


if __name__ == '__main__':
    main()
