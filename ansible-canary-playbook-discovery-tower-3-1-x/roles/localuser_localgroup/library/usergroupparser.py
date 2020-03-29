try:
    import json
except ImportError:
    import simplejson as json

from ansible.module_utils.basic import *


def mergeAll(userHash, groups):

    output = {}
    output['user_group'] = {}
    output['user_group'].update(userHash)
    output['user_group'].update(groups)

    return output

def mergeUserHash(userData, hashData):
    outHashMap = {}
    output = list()

    for i in range(len(userData)):
        user_hash_join = {}
        user_json = parseUser(userData[i])
        hash_json = parseHash(hashData[i])

        user_hash_join.update(user_json)
        user_hash_join.update(hash_json)

        output.append(user_hash_join)

    outHashMap['users'] = output

    return outHashMap


def parseUser(user):

    tmpData = {}
    data = user.encode('utf-8').replace('\"', '').split(":")
    tmpData['username'] = data[0]
    tmpData['encrypted_password'] = data[1]
    tmpData['uid'] = data[2]
    tmpData['gid'] = data[3]
    tmpData['gecos'] = data[4]
    tmpData['home_directory'] = data[5]
    tmpData['login_shell'] = data[6]
    return tmpData


def parseHash(hash):

    tmpData = {}
    data = hash.encode('utf-8').replace('\"', '').split(":")

    tmpData['username'] = data[0]
    tmpData['shadow_password'] = data[1]
    tmpData['shadow_last_changed'] = data[2]
    tmpData['shadow_may_change'] = data[3]
    tmpData['shadow_must_change'] = data[4]
    tmpData['shadow_warn_user'] = data[5]
    tmpData['shadow_expire'] = data[6]
    tmpData['shadow_days_expired'] = data[7]
    tmpData['shadow_expired_date'] = data[8]

    return tmpData


def parseGroups(groupData):
    output = {}
    entries = []
    for group in groupData:
        tmpData = {}
        data = group.encode('utf-8').replace('\"', '').split(":")
        if len(data) > 1:
            tmpData['group_name'] = data[0]
            tmpData['password'] = data[1]
            tmpData['gid'] = data[2]
            tmpData['group_list'] = data[3]
        entries.append(tmpData)

    output['groups'] = entries
    return output


def main():
    fields = {
        "userData": { "required": True, "type": "list" },
        "hashData": {"required": True, "type": "list" },
        "groupData": {"required": True, "type": "list"}
    }

    module = AnsibleModule(argument_spec=fields)

    userHashResults = mergeUserHash(module.params['userData'], module.params['hashData'])
    groupResults = parseGroups(module.params['groupData'])
    outputMap = mergeAll(userHashResults, groupResults)

    module.exit_json(meta=outputMap)


if __name__ == '__main__':
    main()
