# Ansible Migration Factory Discovery Sudoers (amf-discovery-sudoers)

An Ansible Role to parse /etc/sudoers and included files on a Linux/Unix system.  This Role contains a custom Ansible module which returns the parsed sudoers data.

## scan_sudoers Ansible Module
The module can be called with its defaults by invoking a task in the following way:
```yaml
- name: "Scan Sudoers configuration"
  scan_sudoers:
```

### Module Parameters
| Parameter Name | Required | Default Value | Type |
| --- | :---: | --- | :---: |
| N/A | N/A | N/A | N/A |

### Returned Data
```yaml
ansible_facts:
  sudoers:
    include_dir: /etc/sudoers.d
    include_files:
      - /etc/sudoers.d/XPERM_AIX
      - /etc/sudoers.d/TEST_2
    sudoers_files:
      - configuration:
          - Defaults   !visiblepw
          - Defaults    env_reset
          - Defaults    env_keep =  "COLORS DISPLAY HOSTNAME HISTSIZE KDEDIR LS_COLORS"
          - Defaults    env_keep += "MAIL PS1 PS2 QTDIR USERNAME LANG LC_ADDRESS LC_CTYPE"
          - Defaults    env_keep += "LC_COLLATE LC_IDENTIFICATION LC_MEASUREMENT LC_MESSAGES"
          - Defaults    env_keep += "LC_MONETARY LC_NAME LC_NUMERIC LC_PAPER LC_TELEPHONE"
          - Defaults    env_keep += "LC_TIME LC_ALL LANGUAGE LINGUAS _XKB_CHARSET XAUTHORITY"
          - Defaults    secure_path = /usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
          - "root\tALL=(ALL) \tALL"
          - "%wheel\tALL=(ALL)\tALL"
          - "%wheel\tALL=(ALL)\tNOPASSWD: ALL"
        path: /etc/sudoers
      - configuration:
          - Defaults:XAIX_STAFF !requiretty
          - 'XAIX_STAFF ALL=(XAIX_STAFF) NOPASSWD: XSU_ROOT'
        path: /etc/sudoers.d/XPERM_AIX
      ...
```

## License
[MIT](LICENSE)

## Author Information
[Andrew J. Huffman](mailto:ahuffman@redhat.com)
