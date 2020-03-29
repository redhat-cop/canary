# install-webservice
Installs the Ansible Migration Factory web service (Coalmine) on a RHEL 7 node.

## Requirements
* The managed nodes must be able to access the code from GitLab.  Examples will follow.

* Current expectation is to connect as root.

* Current expectation is that this is not to set up dev, it'll set up prod.  Fancier setups pending.


## Variables
| Variable Name | Required | Description | Default Value | Type |
| --- | :---: | --- | :---: | :---: |
| ws_repo_url | yes | Git URL for the webservice code to install | "ssh://git@gitlab.consulting.redhat.com:2222/Canary/ansible-canary-webservice.git" | string |
| ws_repo_accept_host_key | yes | Whether or not to auto-accept the git server's host key to known_hosts.| True | boolean |
| ws_repo_version | yes | Version (tag/commit/branch) of the webservice code to pull/clone. | "HEAD" | string |
| ws_pkgs | yes | List of packages to install for the webservice to run. | ["git","nodejs","sassc"] | list |
| ws_user | yes | Webservice username to create | "canary" | string |
| ws_group | yes | Webservice user's primary group to create/join | "canary" | string |
| ws_user_home | yes | Home directory to create for the `ws_user` | "/home/{{ ws_user }}" | string |
| ws_apache_pkg | yes | Name of the apache package to install. | "httpd" | string |
| ws_apache_svc | yes | Name of the apache service | "httpd" | string |


## Dependencies
Node.js should probably be extracted as a dependency.

## Example Playbook
```yaml
---
- name: "Deploy Ansible Migration Factory Webservice"
  hosts: "webservice-server"
  roles:
     - role: "install-webservice"
       ws_user: "awx"
       ws_group: "awx"
```         

## License
[MIT](LICENSE)

## Author Information
* [Jim Van Fleet](mailto:jim.van.fleet@levvel.io)
* [Andrew J. Huffman](mailto:ahuffman@redhat.com)
