# Project Canary

## Background
Project Canary is a discovery tool that leverages Ansible’s built-in fact collection mechanisms, coupled with extended fact collection modules for sudoers facts, cron facts, local user and group facts, running process facts, and listening port facts.  Additionally, there is a mechanism for identifying application workloads running on each host (application signatures/profiles).  

The application identification process involves the use of a purpose-built Ansible module called application_id, and consists of a scoring system that introspects a subset of the collected facts to simulate the steps taken by a human to identify applications running on a system.  The best way to describe Canary is “Infrastructure as Code in Reverse”.

## Approach
The discovery playbook collects built-in Ansible facts, custom facts, and then runs the collected facts through our application identification system.  Then the playbook creates a new inventory within Ansible Tower, called “Discovered Hosts”, recreates each host from the target inventory, attaches the collected facts as host_vars.  For each application that was discovered, an inventory group gets created, and the host will be associated

The “Discovered Hosts” inventory can then be leveraged to drive future automation and migration efforts.

## Current Application Signatures
We can currently identify the following systems/applications:
- Apache HTTP Server
- Apache Tomcat Application Server
- Automic Automation Engine
- Automic ServiceManager
- Batch Teradata Query (BTEQ)
- Bigfix Agent
- BMC Patrol Agent
- CA SystemEdge Client
- CA Workload Automation Agent
- Catalyst Warehouse Management System
- Commvault Agent
- IBM Cognos Analytics
- IBM Cognos Business Intelligence
- IBM DB2 Database Server
- IBM HTTPD Server
- IBM InfoSphere DataStage
- IBM InfoSphere Information server
- IBM Rational ClearCase
- IBM Sterling Commerce
- IBM  Storage Resource Agent
- IBM Tivoli Monitoring
- IBM TSM Backup/Archive Client
- IBM TSM Server
- IBM WebSphere Application Server
- IBM Websphere Message Broker
- IBM WebSphere MQ Everyplace
- IBM WebSphere MQ File Transfer Edition
- IBM WebSphere MQ Server
- Informatica PowerCenter
- IRI Cosort
- JBoss Application Server
- LPAR2RRD Agent
- Manhattan Warehouse Management
- MicroStrategy Intelligence Server
- Objective Development Sharity
- Opswise Universal Agent
- OptiFlowMgr
- Oracle Automatic Storage Management (ASM)
- Oracle Business Intelligence (OBIEE)
- Oracle Concurrent Manager
- Oracle Configuration Manager
- Oracle Data Integrator (ODI) Agent
- Oracle Enterprise Manager (OEM)
- Oracle ESSBASE Server
- Oracle Glassfish
- Oracle HTTP Server
- Oracle RDBMS
- Oracle Real Application Cluster (RAC)
- Oracle Weblogic Server
- Quest Authentication Services
- RRD Tool
- SAMBA
- SAS Intelligent Planning Suite
- SAS Merchandise Planning Server
- SAS Scalable Performance Data Server
- Tripwire Agent
- WebMethods Integration Server

## Installation and Usage
1. Install Ansible Tower
2. Build a target inventory
3. Clone the Canary installation project:  https://github.com/boogiespook/canary
4. cd to installation-playbooks/ansible-canary-playbook-install-ansible-migration-factory/
5. Follow the instructions in the README.md file to install all of the components into Ansible Tower
6. Review the “Ansible Migration Factory - Discovery” job template in Ansible Tower and ensure it has been installed properly
 - You may want to set the “slice” setting on the job template to the number of nodes in your Ansible Tower cluster or the Instance Group you intend to leverage.
 - You may also want to tweak the default setting of ‘50’ forks on the job template depending on the Tower environment
7. Ensure the tower-cli and jmespath has been installed to the default Ansible Tower virtual environment on each of your Ansible Tower nodes.  
Note: this will automatically be done for you as long as the ‘vars/discovery_vars.yml’ contains “discovered_host_install_prereqs: True”, which is the default.

Some discovery roles have prerequisite requirements based on the particular Operating System target.  A common setting has been created to allow for installing requirements for any required module dependencies to be installed to the hosts.  This setting is in “vars/discovery_vars.yml” and “discovery_install_prereqs: False” by default.  If you have permission from your customer to make changes, you may want to switch this setting to True to enable installation of any required packages on the target Operating Systems.  The playbook will make no modifications to the target systems during a run by default.

Launch the “Ansible Migration Factory - Discovery” job template from your Ansible Tower and you are on your way
