# Ansible Migration Factory Discovery Playbook Roles

## Discovery Roles
To automatically provide Discovery Roles to the Ansible Migration Factory Discovery playbook pluggable framework, the following requirements must be met:
* The Discovery Role directory must be named with a prefix of `amf-discovery-`.  Any directory with that prefix will be auto-included in the playbook run as a Discovery Role.
* The Discovery Role must return its facts as a dictionary in the `ansible_facts` dictionary.

## Application Signature Roles
To automatically provide Application Signature Roles to the Ansible Migration Factory Discovery playbook pluggable framework, the following requirements must be met:
* The Application Signature Role directory must be named with a prefix of `amf-application-signature-`.  Any directory with that prefix will be auto-included in the playbook run as an Application Signature.
* To properly identify an Application with an Application Signature, the role should take the ansible_facts that were collected with the Discovery Playbook and check for several conditions.  Typically you would want to check for any combination of the following:
    * Users
    * Groups
    * Services
    * Packages
    * Paths
    * Running Processes

  If all of your defined conditions are met based on the `ansible_facts` then you should set a fact which appends a dictionary with the keys `name` and `desc` (Application name and Description) to the `discovered_apps` list that is set during the Discovery playbook run.

  An example of this would look like:  

```yaml
- name: "Identify An Application"
  set_fact:
    discovered_apps: "{{ discovered_apps | union([{'name': 'My App Name', 'desc': 'Hosts identified as My App Name'}])}}"
  when:
    - 'condition1 == "something"'
    ...
```
### Developing a new Application Signature Role
* It is recommended to use the Application Signature Role Skeleton to easily get your Application Signature started.  
* Simply clone the [ansible-canary-role-development-application-signature-skeleton](https://gitlab.consulting.redhat.com/Canary/ansible-canary-role-development-application-signature-skeleton) repository.
* Follow the guide in the [ansible-canary-role-development-application-signature-skeleton](https://gitlab.consulting.redhat.com/Canary/ansible-canary-role-development-application-signature-skeleton) repository's README.md to initialize a new Application Signature with the skeleton.
