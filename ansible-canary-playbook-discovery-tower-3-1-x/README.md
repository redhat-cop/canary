####  Introduction
Two playbooks were written for the Discovery Phase, custom facts playbook and regenerate eai csv.

#### Custom Facts Playbook
The purpose of the custom facts playbook is to collect servers facts on a target server, combine the custom facts with Ansible Tower facts and push the data to a backend webservice.

The custom facts roles written for this playbook include:
1. listeners
2. processes
3. local users and groups
4. cron jobs

Ansible Tower provided facts includes:
1. Ansible Facts
2. Ansible Services
3. Ansible Packages

##### Variables required by the playbook
1. **webservice_ip**: WebService IP to post collected facts to
2. **tower_user**: Tower username:
3. **tower_password**: Tower password
4. **parent_app_name**: Parent Application name needed to specify the csv report title

For execution of the playbook, the tower credentials are encrypted in the tower_credentials.yml file

##### Example Execution of the playbook
```
$ ansible-playbook -i inventory -e "webservice_ip=192.168.121.182 parent_app_name=customer_portal" custom_facts_playbook.yml
```

#### Regenerate EAI CSV Playbook
The purpose of this playbook is to enable execute a rest endpoint from the webservice that will regernate the csv fact reports for a particular EAI code of interest.

##### Variables
1. **webservice_ip**: WebService IP to post collected facts to
2. **eai_code**: needed to determine the parent application to recreate csv reports for
