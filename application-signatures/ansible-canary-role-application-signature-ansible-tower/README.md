# amf-application-signature-ansible-tower
An Ansible Role that identifies an Ansible Tower server as part of the Ansible Migration Factory Discovery process.  Use this Role as an example for developing further application signatures.

## Variables
| Variable Name | Description | Type |
| --- | --- | :---: |
| amf_as_services | List of services to check ansible_facts.services for | list |
| amf_as_user_group | Users and groups to check ansible_facts.local_users and ansible_facts.local_groups for. | dictionary |
| amf_as_user_group.users | List of users to check ansible_facts.local_users for | list |
| amf_as_user_group.groups | List of groups to check ansible_facts.local_groups for | list |
| amf_as_paths | List of paths to check the system for | list |
| amf_as_packages | List of packages to check ansible_facts.packages for. | list |
| amf_as_discovered_app | Dictionary containing the name of the application we are identifying and a description of the classification. The dictionary should contain a `name` and `desc` key | dictionary |
## Dependencies
This Role depends on the Ansible Migration Factory Discovery playbook and its custom discovery roles to properly obtain data identifying an application.

## Returned Data
The Role assumes that the fact discovered_apps has been initialized.  This Role will append a dictionary to the `discovered_apps` list with the keys `name` and `desc` (description).

These values are defined in [defaults/main.yml](defaults/main.yml) in the amf_as_discovered_app dictionary.

```yaml
discovered_apps: "{{ discovered_apps | union([amf_as_discovered_app]) }}"
```
This is only returned when the defined conditions are met.

## License
[MIT](LICENSE)

## Author Information
[Andrew J. Huffman](mailto:ahuffman@redhat.com)
