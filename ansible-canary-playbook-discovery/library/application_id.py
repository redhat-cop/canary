#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: application_id
short_description: Identifies running applications by a scoring system
version_added: "2.8"
description:
    - "A set of users, groups, processes, ports, paths, packages, and services, along with collected ansible_facts, the application name and description to tag with, and acceptable scores for each category are passed into the module"
    - "This module returns the application name and description as ansible_facts['discovered_apps'].  If ansible_facts['discovered_apps'] already exists, this module will take the existing value and append any new values to it, otherwise the module will skip making no changes if an application is not identified with the scoring criteria provided."
application:
    description:
        - Information to "tag" a system with if the system meets all conditions from the scores option.  This is a dictionary with a name and desc field.
    type: dictionary
    default: {}
    required: True
application.name:
    description:
        - Name of the application to tag the system with if all conditions from the scores parameter are satisfied.
    type: string
    required: True
application.desc:
    description:
        - Description of the application to tag the system with if all conditions from the scores parameter are satisfied.
    type: string
    required: True
users:
    description:
        - List of users to check when identifying a running application.
    type: list
    default: []
    required: True
groups:
    description:
        - List of groups to check when identifying a running application.
    type: list
    default: []
    required: True
packages:
    description:
        - List of packages to check when identifying a running application.
    type: list
    default: []
    required: True
paths:
    description:
        - List of paths to check when identifying a running application.
    type: list
    default: []
    required: True
ports:
    description:
        - List of listening ports to check when identifying a running application.
    type: list
    default: []
    required: True
processes:
    description:
        - List of processes to check when identifying a running application.  Can be a partial process name to keep generic across OS platforms.
    type: list
    default: []
    required: True
services:
    description:
        - List of services to check when identifying a running application.
    type: list
    default: []
    required: True
facts:
    description:
        - Dictionary of collected ansible_facts to perform the application identification against.  The expected format is based on ansible-fact and Ansible Migration Factory's discovery process.
    type: dictionary
    default: {}
    required: True
scores:
    description:
        - Dictionary of scores required to properly identify the running application.  Each category requires an integer that represents the minimum count that will satisfy a >= evaluation.
        - As an example, if there are 2 users that could be present, but only 1 required to be present to identify an application, set the scores.users value to "1".
    type: dictionary
    default: {}
    required: True
scores.groups:
    description:
        - Integer that represents the minimum number of groups that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
scores.users:
    description:
        - Integer that represents the minimum number of users that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
scores.paths:
    description:
        - Integer that represents the minimum number of paths that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
scores.ports:
    description:
        - Integer that represents the minimum number of ports that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
scores.packages:
    description:
        - Integer that represents the minimum number of packages that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
scores.processes:
    description:
        - Integer that represents the minimum number of processes that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
scores.services:
    description:
        - Integer that represents the minimum number of services that must be present in order to identify an application.  The evaluation is performed by a >= check of this score.
    type: integer
    default: None
    required: True
discovered_apps:
    description:
        - List of currently set ansible_facts['discovered_apps'], with each list item containing a dictionary with 'name' and 'desc' keys set.
    type: list
    default: []
    required: True

author:
    - Andrew J. Huffman (@ahuffman)
'''

EXAMPLES = '''
# Identify Oracle Database
- name: "Identify Oracle Database Server"
  application_id:
    facts: "{{ ansible_facts }}"
    services: []
    users:
      - "oracle"
    groups:
      - "dba"
    paths:
      - "/etc/oratab"
      - "/etc/oraInst.loc"
    packages: []
    ports: []
    processes:
      - "ora_mmon_"
      - "ora_pmon_"
      - "ora_smon_"
      - "tnslsnr"
    application:
      name: "Oracle Database"
      desc: "Hosts identified as Oracle Database Servers"
    scores:
      users: 0
      groups: 0
      paths: 1
      packages: 0
      ports: 0
      processes: 3
      services: 0
'''

RETURN = '''
ansible_facts['discovered_apps']:
  - name: "Oracle Database"
    desc: "Hosts identified as Oracle Database Servers"
'''

from ansible.module_utils.basic import AnsibleModule
from os.path import exists

def main():
    module_args = dict(
        services=dict(
            type='list',
            default=list(),
            required=False
        ),
        users=dict(
            type='list',
            default=list(),
            required=False
        ),
        groups=dict(
            type='list',
            default=list(),
            required=False
        ),
        paths=dict(
            type='list',
            default=list(),
            required=False
        ),
        packages=dict(
            type='list',
            default=list(),
            required=False
        ),
        processes=dict(
            type='list',
            default=list(),
            required=False
        ),
        ports=dict(
            type='list',
            default=list(),
            required=False
        ),
        application=dict(
            type='dict',
            default=dict(
                name="",
                desc=""
            ),
            required=False
        ),
        scores=dict(
            type='dict',
            default=dict(
                users=0,
                groups=0,
                services=0,
                paths=0,
                packages=0,
                processes=0,
                ports=0
            ),
            required=False
        ),
        facts=dict(
            type='dict',
            required=True
        ),
        discovered_apps=dict(
            type='list',
            default=list(),
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

    # if params['output_ps_stdout_lines']:
    #     processes['ps_stdout_lines'] = raw_procs['stdout']
    # if params['output_parsed_processes']:
    #     processes['total_running_processes'] = raw_procs['total_running_processes']
    #     processes['processes'] = proc_data
    # result = {'ansible_facts': {'running_processes': processes}}
    #
    # module.exit_json(**result)

    # init counters
    svc_count = 0
    user_count = 0
    group_count = 0
    path_count = 0
    pkg_count = 0
    proc_count = 0
    port_count = 0

    chk_services = params['services']
    chk_users = params['users']
    chk_groups = params['groups']
    chk_paths = params['paths']
    chk_packages = params['packages']
    chk_processes = params['processes']
    chk_ports = params['ports']
    app_id = params['application']
    scores = params['scores']
    facts = params['facts']
    apps = params['discovered_apps']

    # check services
    if len(chk_services) > 0 and facts.get('services'):
        for s in chk_services:
            if facts['services'].get(s) is not None:
                svc_count += 1

    # check users
    if len(chk_users) > 0 and facts.get('local_users'):
        # build user list
        user_list = list()
        for u in facts['local_users']:
            if u.get('user') is not None:
                user_list.append(u['user'])
        for u in chk_users:
            if u in user_list:
                user_count += 1

    # check groups
    if len(chk_groups) > 0 and facts.get('local_groups'):
        # build group list
        group_list = list()
        for g in facts['local_groups']:
            if g.get('group') is not None:
                group_list.append(g['group'])
        for g in chk_groups:
            if g in group_list:
                group_count += 1

    # check packages
    if len(chk_packages) > 0 and facts.get('packages'):
        for p in chk_packages:
            if facts['packages'].get(p) is not None:
                pkg_count += 1

    # check processes
    if 'running_processes' in facts and 'processes' in facts['running_processes'] and len(chk_processes) > 0 and len(facts['running_processes']['processes']) > 0:
        # build process list
        proc_list = list()
        for p in facts['running_processes']['processes']:
            if p.get('command') is not None:
                proc_list.append(p['command'])
        for p in chk_processes:
            for proc in proc_list:
                if str(p) in str(proc):
                    proc_count += 1

    # check ports
    # if len(chk_ports) > 0:
    #     for p in chk_ports:

    # check paths
    if len(chk_paths) > 0:
        for p in chk_paths:
            if exists(p):
                path_count += 1

    # Scoring
    if svc_count >= int(scores['services']) and user_count >= int(scores['users']) and \
        group_count >= int(scores['groups']) and path_count >= int(scores['paths']) and \
        pkg_count >= int(scores['packages']) and proc_count >= int(scores['processes']) and \
        port_count >= int(scores['ports']):
        # App is identified
        if len(apps) < 1:
            discovered_apps = [app_id]
        else:
            discovered_apps = apps
            discovered_apps.append(app_id)
        result = {'ansible_facts': {'discovered_apps': discovered_apps}, 'changed': True, 'msg': {'user_count': user_count, 'group_count': group_count,'svc_count': svc_count, 'port_count': port_count, 'proc_count': proc_count, 'pkg_count': pkg_count, 'path_count': path_count}}
        module.exit_json(**result)
    else:
        # not identified
        result['msg'] = {'user_count': user_count, 'group_count': group_count,'svc_count': svc_count, 'port_count': port_count, 'proc_count': proc_count, 'pkg_count': pkg_count, 'path_count': path_count}
        result['skipped'] = True
        module.exit_json(**result)
if __name__ == '__main__':
    main()
