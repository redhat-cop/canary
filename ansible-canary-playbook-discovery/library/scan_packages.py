#!/usr/bin/env python

from ansible.module_utils.basic import * # noqa

DOCUMENTATION = '''
---
module: scan_packages
short_description: Return installed packages information as fact data
description:
    - Return information about installed packages as fact data
version_added: "1.9"
options:
requirements: [ ]
author: Matthew Jones
'''

EXAMPLES = '''
# Example fact output:
# host | success >> {
#    "ansible_facts": {
#        "packages": {
#              "libbz2-1.0": [
#                {
#                  "version": "1.0.6-5",
#                  "source": "apt",
#                  "arch": "amd64",
#                  "name": "libbz2-1.0"
#                }
#              ],
#              "patch": [
#                {
#                  "version": "2.7.1-4ubuntu1",
#                  "source": "apt",
#                  "arch": "amd64",
#                  "name": "patch"
#                }
#              ],
#              "gcc-4.8-base": [
#                {
#                  "version": "4.8.2-19ubuntu1",
#                  "source": "apt",
#                  "arch": "amd64",
#                  "name": "gcc-4.8-base"
#                },
#                {
#                  "version": "4.9.2-19ubuntu1",
#                  "source": "apt",
#                  "arch": "amd64",
#                  "name": "gcc-4.8-base"
#                }
#              ]
#       }
'''


def rpm_package_list(module):
    HAS_RPM_LIB = False
    trans_set = None
    try:
        import rpm
        trans_set = rpm.TransactionSet()
        HAS_RPM_LIB = True
    except Exception as e:
        module.warn('Unable to import RPM, packages can not be scanned for')

    installed_packages = {}
    if HAS_RPM_LIB:
        for package in trans_set.dbMatch():
            package_details = dict(name=package[rpm.RPMTAG_NAME],
                                   version=package[rpm.RPMTAG_VERSION],
                                   release=package[rpm.RPMTAG_RELEASE],
                                   epoch=package[rpm.RPMTAG_EPOCH],
                                   arch=package[rpm.RPMTAG_ARCH],
                                   source='rpm')
            if package_details['name'] not in installed_packages:
                installed_packages[package_details['name']] = [package_details]
            else:
                installed_packages[package_details['name']].append(package_details)
    return installed_packages


def deb_package_list():
    import apt
    apt_cache = apt.Cache()
    installed_packages = {}
    apt_installed_packages = [pk for pk in apt_cache.keys() if apt_cache[pk].is_installed]
    for package in apt_installed_packages:
        ac_pkg = apt_cache[package].installed
        package_details = dict(name=package,
                               version=ac_pkg.version,
                               arch=ac_pkg.architecture,
                               source='apt')
        if package_details['name'] not in installed_packages:
            installed_packages[package_details['name']] = [package_details]
        else:
            installed_packages[package_details['name']].append(package_details)
    return installed_packages

def aix_package_list(module):
    lslpp_path = module.get_bin_path("lslpp")
    rc, stdout, stderr = module.run_command('%s -L -q -c' % lslpp_path, use_unsafe_shell=True)
    installed_packages = dict()
    for line in stdout.split('\n'):
        # lslpp output key
        # State codes:
        #  A -- Applied.
        #  B -- Broken.
        #  C -- Committed.
        #  E -- EFIX Locked.
        #  O -- Obsolete.  (partially migrated to newer version)
        #  ? -- Inconsistent State...Run lppchk -v.
        #
        # Type codes:
        #  F -- Installp Fileset
        #  P -- Product
        #  C -- Component
        #  T -- Feature
        #  R -- RPM Package
        #  E -- Interim Fix
        # Field info:
        #Package Name:Fileset:Level:State:PTF Id:Fix State:Type:Description:Destination Dir.:Uninstaller:Message Catalog:Message Set:Message Number:Parent:Automatic:EFIX Locked:Install Path:Build Date
        pkg = line.split(':')
        if len(pkg) == 18:
            installed_packages[pkg[0]] = list()
            package_details = dict(name=pkg[0],
                                    fileset=pkg[1],
                                    version=pkg[2],
                                    ptf_id=pkg[4],
                                    description=pkg[7],
                                    destination_dir=pkg[8],
                                    uninstaller=pkg[9],
                                    message_catalog=pkg[10],
                                    message_set=pkg[11],
                                    message_number=pkg[12],
                                    parent=pkg[13],
                                    automatic=pkg[14],
                                    efix_locked=pkg[15],
                                    install_path=pkg[16],
                                    build_date=pkg[17],
                                    source='installp')
            # Type codes
            if pkg[6] == 'F':
                package_details['type'] = "installp_fileset"
            elif pkg[6] == 'P':
                package_details['type'] = "product"
            elif pkg[6] == 'C':
                package_details['type'] = "component"
            elif pkg[6] == 'T':
                package_details['type'] = "feature"
            elif pkg[6] == 'R':
                package_details['type'] = "rpm"
            elif pkg[6] == 'E':
                package_details['type'] = "interim_fix"
            else:
                package_details['type'] = pkg[6]
            # State codes
            if pkg[3] == 'A':
                package_details['state'] = "applied"
            elif pkg[3] == 'B':
                package_details['state'] = "broken"
            elif pkg[3] == 'C':
                package_details['state'] = "committed"
            elif pkg[3] == 'E':
                package_details['state'] = "efix_locked"
            elif pkg[3] == 'O':
                package_details['state'] = "obsolete"
            elif pkg[3] == '?':
                package_details['state'] = "inconsistent_state"
            else:
                package_details['state'] = pkg[3]
            # Fix States
            if pkg[5] == 'A':
                package_details['fix_state'] = "applied"
            elif pkg[5] == 'B':
                package_details['fix_state'] = "broken"
            elif pkg[5] == 'C':
                package_details['fix_state'] = "committed"
            elif pkg[5] == 'E':
                package_details['fix_state'] = "efix_locked"
            elif pkg[5] == 'O':
                package_details['fix_state'] = "obsolete"
            elif pkg[5] == '?':
                package_details['fix_state'] = "inconsistent_state"
            else:
                package_details['fix_state'] = pkg[5]

            installed_packages[pkg[0]].append(package_details)
    return installed_packages

def main():
    module = AnsibleModule(
        argument_spec = dict(os_family=dict(required=True))
    )
    ans_os = module.params['os_family']
    if ans_os in ('RedHat', 'Suse', 'openSUSE Leap'):
        packages = rpm_package_list(module)
    elif ans_os == 'Debian':
        packages = deb_package_list()
    elif ans_os == 'AIX':
        packages = aix_package_list(module)
    else:
        packages = None

    if packages is not None:
        results = dict(ansible_facts=dict(packages=packages))
    else:
        results = dict(skipped=True, msg="Unsupported Distribution: {}".format(ans_os))
    module.exit_json(**results)


main()
