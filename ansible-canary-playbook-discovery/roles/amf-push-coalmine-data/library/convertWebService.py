#!/usr/bin/python

from ansible.module_utils.basic import *

try:
    import json
except:
    import simplejson as json


def convertAnsibleFacts (ansible_facts, parent_app_name, hostname):

    webServiceOutput = dict()
    webServiceOutput['parent_app_name'] = parent_app_name
    webServiceOutput['hostname'] = hostname.lower()
    facts_formatted = dict()
    # coalmine webservice requires ansible_ fact prefixes from pre ansible 2.5
    for key in ansible_facts.keys():
        facts_formatted['ansible_' + key] = ansible_facts[key]
    webServiceOutput['facts'] = facts_formatted
    # fix formatting of packages for coalmine webservice
    pkgs_formatted = list()
    for pkg in ansible_facts['packages'].keys():
        pkgs_formatted.append(ansible_facts['packages'][pkg][0])
    webServiceOutput['packages'] = pkgs_formatted
    # fix formatting of services for coalmine webservice
    svcs_formatted = list()
    for svc in ansible_facts['services'].keys():
        svcs_formatted.append(ansible_facts['services'][svc])
    webServiceOutput['services'] = svcs_formatted
    return (webServiceOutput)

def addCustomFacts(custom_facts, webServiceOutput):
    for fact in custom_facts:
        keys = fact.keys()
        webServiceOutput[keys[0]] = fact[keys[0]]
    return webServiceOutput

def main():
    fields = {
        "ansible_facts": { "required": True, "type": "dict" },
        "hostname" : {"required": True, "type": "str"},
        "parent_app_name": {"required": True, "type": "str"},
        "custom_facts_list" : {"required": False, "type": "list"}
    }

    module = AnsibleModule(argument_spec=fields)

    webServiceOutput = convertAnsibleFacts(module.params['ansible_facts'], module.params['parent_app_name'], module.params['hostname'])

    if module.params['custom_facts_list'] is not None:
        addCustomFacts(module.params['custom_facts_list'], webServiceOutput)

    module.exit_json(meta=webServiceOutput)

if __name__ == '__main__':
    main()
