# amf-application-signature-webmethods-integration-server
An Ansible Role that identifies an WebMethods Integration Server as part of the Ansible Migration Factory Discovery process.

## Variables
| Variable Name | Description | Type |
| --- | --- | :---: |
| amf_as_services | List of services to check ansible_facts.services for | list |
| amf_as_user_group | Users and groups to check ansible_facts.local_users and ansible_facts.local_groups for. | dictionary |
| amf_as_user_group.users | List of users to check ansible_facts.local_users for | list |
| amf_as_user_group.groups | List of groups to check ansible_facts.local_groups for | list |
| amf_as_paths | List of paths to check the system for existence | list |
| amf_as_packages | List of packages to check ansible_facts.packages for. | list |
| amf_as_ports | List of ports to check ansible_facts.tcp_listen and ansible_facts.udp_listen for. Not fully implemented (please do not use yet, and set your ports score to '0')| list |
| amf_as_discovered_app | Dictionary containing the name of the application we are identifying and a description of the classification. The dictionary should contain a `name` and `desc` key | dictionary |
| amf_as_processes | List of process names to check ansible_facts.running_processes.processes[*].command for, can be partial process names.| list |
| amf_as_scores | Dictionary containing categories for the number of minimum "hits" represented as an integer per category. | dictionary |
| amf_as_scores.users | Minimum number of users to be evaluated as >= by the scoring system | integer |
| amf_as_scores.groups | Minimum number of groups to be evaluated as >= by the scoring system | integer |
| amf_as_scores.packages | Minimum number of packages to be evaluated as >= by the scoring system | integer |
| amf_as_scores.paths | Minimum number of paths to be evaluated as >= by the scoring system | integer |
| amf_as_scores.ports | Minimum number of ports to be evaluated as >= by the scoring system | integer |
| amf_as_scores.processes | Minimum number of processes to be evaluated as >= by the scoring system. | integer |
| amf_as_scores.services | Minimum number of services to be evaluated as >= by the scoring system. | integer |


## Dependencies
This Role depends on the Ansible Migration Factory Discovery playbook and its custom discovery roles to properly obtain data identifying an application.  The collected custom facts are passed into the `application_id` module where the scoring work is completed.

## Returned Data
This Role will append a dictionary to the `ansible_facts.discovered_apps` list with the keys `name` and `desc` (description).  This data is used to tag the systems with the identified applications, based on application signature roles.

These values are defined in [defaults/main.yml](defaults/main.yml) in the amf_as_discovered_app dictionary.
This is only returned when the defined conditions are met.

## License
[MIT](LICENSE)

## Author Information
[David Castellani](mailto:dave@redhat.com)
[Andrew J. Huffman](mailto:huffy@redhat.com)