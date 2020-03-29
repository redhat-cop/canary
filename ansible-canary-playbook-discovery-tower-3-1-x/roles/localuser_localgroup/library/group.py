try:
    import json
except ImportError:
    import simplejson as json

from ansible.module_utils.basic import *

def parseGroups(groupData):
    output = {}
    entries = []
    for group in groupData:
        tmpData = {}
        data = group.encode('utf-8').replace('\"', '').split(":")
        tmpData['group_name'] = data[0]
        tmpData['password'] = data[1]
        tmpData['gid'] = data[2]
        tmpData['group_list'] = data[3]
        entries.append(tmpData)

    output['groups'] = entries
    return output

def main():
    fields = {
        "groupData": { "required": True, "type": "list" }
    }

    module = AnsibleModule(argument_spec=fields)

    output = parseGroups(module.params['groupData'])

    module.exit_json(meta=output)

if __name__ == '__main__':
    main()
