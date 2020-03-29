#!/usr/bin/python

from ansible.module_utils.basic import *

try:
    import json
except:
    import simplejson as json


def convertAnsibleFacts (ansible_facts, parent_app_name, hostname):

    webServiceOutput = {}
    webServiceOutput['parent_app_name'] = parent_app_name
    webServiceOutput['hostname'] = hostname.lower()
    webServiceOutput['facts']=ansible_facts[0]
    webServiceOutput['packages']=ansible_facts[1]
    webServiceOutput['services']=ansible_facts[2]

    return (webServiceOutput)

def addCustomFacts(custom_facts, webServiceOutput):

    for fact in custom_facts:
        data = fact['meta']
        for subkey in data.keys():
            webServiceOutput[subkey] = data[subkey]
    return webServiceOutput

def main():

    fields = {
        "ansible_facts": { "required": True, "type": "list" },
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
