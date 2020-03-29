# Ansible Migration Factory Discovery Installation

## Description
This playbook will install and configure the Ansible Migration Factory Discovery tools into Ansible Tower.
The playbook will install all prerequisite python packages to the Operating System and default Ansible Tower virtual environment, setup required credentials, projects, custom credential types, and job templates into the Ansible Tower instance(s).

## How-to Run
1. Clone the repository onto the Ansible Tower Server
2. Ensure you have Ansible installed
3. Edit the [vars/defaults.yml](vars/defaults.yml) file to fit your environment.
4. Edit the [inventory](inventory) file.
5. Run the playbook | `ansible-playbook install-ansible-migration-factory.yml -i ./inventory`
  - You will be prompted for the Ansible Tower admin user's password.
  - This password is for the `tower_user` specified in [vars/defaults.yml](vars/defaults.yml).
  - In addition you will be prompted for a service account username/password. These are used to create a machine credential to get into the remote boxes.

## Variables - Defaults (vars/defaults.yml)
These variables should be modified prior to running the installation playbook.

| Variable Name | Description | Default Value | Type |
| --- | --- | --- | --- |
| tower_url | The URL, starting with `https://` to the Ansible Tower Instance. Do not include a trailing forward slash (`/`). | "" | string |
| tower_verify_ssl | Whether or not to validate the Ansible Tower instance's SSL certificate.  If using self-signed certificates, this should be 'False'. | False | boolean |
| tower_user | An Ansible Tower user that has administrative rights to the Ansible Tower instance. | "admin" | string |
| tower_org | The Ansible Tower organization you would like to install the Ansible Migration Factory discovery tools into. | "Default" | string |
| amf_jt_disc_inv | The name of the target inventory to perform Ansible Migration Factory's discovery process against. | "Demo Inventory" | string |

## Variables - Other Configurables (vars/required.yml)
Other variables that adjust how the Ansible Tower objects get named are found in [vars/required.yml](vars/required.yml).  It's recommended to not touch these values unless you have a good reason to.

| Variable Name | Description | Default Value | Type |
| --- | --- | --- | --- |
| amf_cred_scm_deploy_user | Ansible Migration Factory repository deploy user (read-only user with clone access) | "gitlab+deploy-token-40" | string |
| amf_cred_scm_deploy_token | Ansible Migration Factory repository deploy key | "TZPxzYfGFuFE11rAoyBb" | string |
| amf_cred_scm_desc | Description of the Ansible Migration Factory SCM credential | "Ansible Migration Factory Access (read-only)" | string |
| amf_cred_scm_name | Name of the Ansible Migration Factory SCM credential | "Ansible Migration Factory" | string |
| amf_scm_name | Name of the Ansible Migration Factory SCM project | "Ansible Migration Factory - Discovery" | string |
| amf_scm_desc | Description of the Ansible Migration Factory SCM project | "Ansible Migration Factory Discovery Playbooks" | string |
| amf_scm_url | URL to the Ansible Migration Factory git repository | "https://gitlab.consulting.redhat.com/Canary/ansible-canary-playbook-discovery.git" | string |
| amf_cred_cust_type_name | Name of the custom credential type | "Ansible Migration Factory" | string |
| amf_cred_cust_name | Name of the Ansible Migration Factory custom credential for Tower API access | "Ansible Tower API" | string |
| amf_cred_discovery_name | Name of the machine credential to perform discoveries with | "Ansible Migration Factory Discovery" | string |
| amf_cred_discovery_desc | Description of the machine credential to perform discoveries with | "Ansible Migration Factory user to perform system discoveries" | string |
| amf_cred_tower_desc | Description of the Ansible Migration Factory custom credential for Tower API access | "Credential required to access Ansible Tower's API" | string |
| amf_jt_disc_name | Name of the Ansible Migration Factory discovery job template | "Ansible Migration Factory - Discovery" | string |
| amf_jt_disc_desc | Description of the Ansible Migration Factory discovery job template | "Playbook to perform the Ansible Migration Factory discovery process" | string |
| amf_jt_disc_proj | Name of the project to attach to the Ansible Migration Factory discovery job template | "{{ amf_scm_name }}" | string |
| amf_jt_disc_play | Playbook to attach to the Ansible Migration Factory discovery job template | "discovery.yml" | string |
| amf_jt_forks | Number of forks to use when running the Ansible Migration Factory discovery job template | 50 | integer |


## Author

[Andrew J. Huffman](mailto:<huffy@redhat.com>)
