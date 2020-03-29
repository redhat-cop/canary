# Ansible Migration Factory Discovery Playbook
An Ansible playbook to conduct system discoveries on Unix/Linux systems with Ansible Tower.

## Variables
See the `discovered_hosts_inventory` role for many variables that can be overridden.

### Required Variables
| Variable Name | Required | Description | Default Value | Type |
|---|---|---|---|---|
|tower_url|no|URL to the Ansible Tower server starting with 'https://'|TOWER_HOST from env or ""|string|
|tower_verify_ssl|no|Validate the Ansible Tower Server's SSL certificate|TOWER_VERIFY_SSL from env or False|boolean|
|tower_user|no|Ansible Tower user with access to create inventories|TOWER_USERNAME from env or ""|string|
|tower_pass|no|Password to the Ansible Tower user.|TOWER_PASSWORD from env or ""|string|
|tower_org|yes|Organization of the Ansible Tower, where the new discovery inventory will be created|""|string|



## License
[MIT](LICENSE)

## Author Information
[Andrew J. Huffman](mailto:ahuffman@redhat.com)
