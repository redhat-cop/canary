# Ansible Migration Factory Discovery Users and Groups (amf-discovery-user-group)

An Ansible Role to parse local users and groups on a Linux/Unix system.  This Role contains a custom Ansible module which returns the parsed local users and groups from /etc/passwd, /etc/shadow, /etc/group, and /etc/gshadow.

## scan_user_group Ansible Module
The module can be called with its defaults by invoking a task in the following way:
```yaml
- name: "Scan Local Users and Groups"
  scan_user_group:
```

### Module Parameters
| Parameter Name | Required | Default Value | Type |
| --- | :---: | --- | :---: |
| passwd_path | no | /etc/passwd | string |
| shadow_path | no | /etc/shadow | string |
| group_path | no | /etc/group | string |
| gshadow_path | no | /etc/gshadow | string |

### Returned Data
```yaml
ansible_facts:
  local_users:
    - user: string
      shadow: boolean
      uid: string
      gid: string
      comment: string
      home: string
      shell: string
      encrypted_password: string
      last_pw_change: string
      min_pw_age: string
      max_pw_age: string
      pw_warning_days: string
      pw_inactive_days: string
      account_expiration: string
      reserved: string(should always be empty...it's a future field)
    - user: ...

  local_groups:
    - group: string
      gshadow: boolean
      gid: string
      members:
        - user1
        - user2
      encrypted_password: string
      administrators:
        - user1
        - user2
    - group: ...
```

## License
[MIT](LICENSE)

## Author Information
[Andrew J. Huffman](mailto:ahuffman@redhat.com)
