#!/usr/bin/python

# Copyright: (c) 2019, Andrew J. Huffman <ahuffman@redhat.com>
# GNU General Public License v3.0+ (see COPYING or https://www.gnu.org/licenses/gpl-3.0.txt)

ANSIBLE_METADATA = {'metadata_version': '1.1',
                    'status': ['preview'],
                    'supported_by': 'community'}

DOCUMENTATION = '''
---
module: package_repository_facts
short_description: Collects all package repositories on a system
version_added: "2.8"
description:
    - "Collects the repository data from package repositories on a system."
    - "This module presents the package repository data and returns the configuration data as ansible_facts"

author:
    - Andrew J. Huffman (@ahuffman)
'''

EXAMPLES = '''

'''

RETURN = '''

'''

from ansible.module_utils.basic import AnsibleModule
import ansible.module_utils.facts.system.distribution as dist
import os, re, platform
from os.path import isfile, isdir, join, splitext
try:
    import configparser
except:
    pass
    import ConfigParser as configparser

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

    def get_yum_repodata():
        yum_repo_files = list()
        all = dict()
        # collects list of .repo files in /etc/yum.repos.d
        def get_repo_files():
            # ensure we collect any repos and configs hiding in default yum config
            yum_repo_paths = ['/etc/yum.conf']
            yum_repos_d = "/etc/yum.repos.d"
            # get only .repo files for parsing
            yum_repo_paths += [join(yum_repos_d, filename) for filename in os.listdir(yum_repos_d) if isfile(join(yum_repos_d, filename)) and list(splitext(filename))[1] == ".repo"]

            return yum_repo_paths

        repo_files = get_repo_files()

        for repo in repo_files:
            yum_repo = dict()
            yum_repo['path'] = repo
            yum_repo['sections'] = list()

            try:
                repo_config = configparser.ConfigParser()
                repo_config.read(repo)
                repo_sections = repo_config.sections()
                for s in repo_sections:
                    section = {'section': s}
                    section.update(dict(repo_config.items(s)))
                    yum_repo['sections'].append(section)
            except:
                pass

            yum_repo_files.append(yum_repo)
        return yum_repo_files


    # def get_deb_repodata():


    # Do work
    ## Get OS family by recycling ansible distro code
    distro_facts = dist.DistributionFactCollector()
    os_facts = distro_facts.collect(module)
    os_family = os_facts['os_family']

    ## Enterprise Linux - based
    if os_family == "RedHat":
        repos = get_yum_repodata()

    ## Debian Linux - based
    # if os_family == "Debian":

    ## Suse
    # if os_family == "Suse":

    ## Archlinux

    ## Mandrake

    ## Solaris

    ## Slackware

    ## Altlinux

    ## SGML

    ## Gentoo

    ## Alpine

    ## AIX

    ## HP-UX

    ## Darwin

    ## FreeBSD

    ## ClearLinux


    # Build output
    repositories = dict()
    # repositories['os_family'] = os_family
    repositories['repository_files'] = repos

    result = {'ansible_facts': {'package_repositories': repositories }}

    module.exit_json(**result)


if __name__ == '__main__':
    main()
