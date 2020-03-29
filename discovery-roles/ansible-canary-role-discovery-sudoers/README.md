# Ansible Migration Factory Discovery Sudoers (amf-discovery-sudoers)

An Ansible Role to parse /etc/sudoers and included files on a Linux/Unix system.  This Role contains a custom Ansible module which returns the parsed sudoers data.

## scan_sudoers Ansible Module
The module can be called with its defaults by invoking a task in the following way:
```yaml
- name: "Scan Sudoers configuration"
  scan_sudoers:
```

### Module Parameters
| Parameter Name | Description | Required | Default Value | Type |
| --- | --- | --- | :---: | :---: |
| output_raw_configs | Whether or not to output the raw (non-comment) configuration lines | no | True | boolean |
| output_parsed_configs | Whether or not to parse the configuration into fact data | no | True | boolean |

### Returned Data Sample
```yaml
ansible_facts:
  sudoers:
    all_scanned_files:
    - /etc/sudoers.d/XPERM_AIX
    - /etc/sudoers.d/TEST_2
    - /etc/sudoers
    sudoers_files:
    - aliases:
        cmnd_alias:
        - commands:
          - /bin/ping
          name: PING
        - commands:
          - /bin/ping
          - /sbin/ifconfig
          name: TEST
        - commands:
          - /usr/sbin/mkfs
          name: BEST
        host_alias:
        - hosts:
          - huff-satellite
          - huff-rhel7
          name: SOMEHOSTS
        - hosts:
          - test1
          - test2
          - test3
          name: SOMEGHOSTS
        - hosts:
          - thathost1
          name: ANOTHER
        runas_alias:
        - name: HUFF
          users:
          - ahuffman
          - root
        - name: TEST
          users:
          - root
        user_alias:
        - name: HUFF
          users:
          - ahuffman
          - root
        - name: TEST
          users:
          - root
      defaults:
      - '!visiblepw'
      - env_reset
      - secure_path:
        - /usr/local/sbin
        - /usr/local/bin
        - /usr/sbin
        - /usr/bin
        - /sbin
        - /bin
      - env_keep:
        - COLORS
        - DISPLAY
        - HOSTNAME
        - HISTSIZE
        - KDEDIR
        - LS_COLORS
        - MAIL
        - PS1
        - PS2
        - QTDIR
        - USERNAME
        - LANG
        - LC_ADDRESS
        - LC_CTYPE
        - LC_COLLATE
        - LC_IDENTIFICATION
        - LC_MEASUREMENT
        - LC_MESSAGES
        - LC_MONETARY
        - LC_NAME
        - LC_NUMERIC
        - LC_PAPER
        - LC_TELEPHONE
        - LC_TIME
        - LC_ALL
        - LANGUAGE
        - LINGUAS
        - _XKB_CHARSET
        - XAUTHORITY
      include_directories:
      - /etc/sudoers.d
      include_files:
      - /etc/sudoers.d/XPERM_AIX
      - /etc/sudoers.d/TEST_2
      path: /etc/sudoers
      user_specifications:
      - commands:
        - ALL
        hosts:
        - ALL
        operators:
        - ALL
        users:
        - root
      - commands:
        - ALL
        hosts:
        - ALL
        operators:
        - ALL
        tags:
        - NOPASSWD
        users:
        - '%wheel'
      - commands:
        - /sbin/mount /mnt/cdrom
        - /sbin/umount /mnt/cdrom
        hosts:
        - ALL
        users:
        - ahuffman
...
```

## License
[MIT](LICENSE)

## Author Information
[Andrew J. Huffman](mailto:ahuffman@redhat.com)
