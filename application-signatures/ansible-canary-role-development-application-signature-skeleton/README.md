# Ansible Migration Factory Application Signature Role Development Skeleton
## Getting Started
### Using the Application Signature Role skeleton
* Clone this repository
* Initialize a new Ansible Application Signature Role from this repository by running the following:

```bash
ansible-galaxy init --role-skeleton=/path/to/cloned/repository amf-application-signature-ROLE-NAME-HERE
```
* Modify the variables in defaults/main.yml as needed to identify your application accordingly
* Categories where there are no items to check, ensure your lists are still empty by using `[]` (empty list) format to ensure the `application_id.py` module functions correctly.
* Use the scoring variables to help you identify your application
  * For example, Ansible Tower has services rabbitmq-server, nginx, and supervisord on every Tower node.  Sometimes, in a single-node configuration the server could have postgresql-9.6 as a service.  Therefore, we look for all these services, but we allow a count greater than or equal to 3 service matches to account for different (clustered) configurations.  We could optionally build more detailed application signatures that identify a single-node Ansible Tower server VS. a single-node (all-in-one) configuration.  Use this methodology to help you define and implement your application profiles.  Remember, the scores are evaluated as `number of hits found within the passed in facts >= score`.
* See the auto-generated README.md after initializing a new application signature with the ansible-galaxy command for more details on what each variable in [defaults/main.yml](defaults/main.yml) does.
* You may also want to check out the `application_id.py` documentation, which can be found in this repository: [ansible-canary-playbook-discovery](https://gitlab.consulting.redhat.com/Canary/ansible-canary-playbook-discovery).
  - The module is in the `library` directory in the repository above.

## License
[MIT](LICENSE)

## Author Information
[Andrew J. Huffman](mailto:ahuffman@redhat.com)
