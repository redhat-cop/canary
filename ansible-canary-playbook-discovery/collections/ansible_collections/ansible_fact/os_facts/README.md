# ansible-fact
[![Build Status](https://travis-ci.com/ahuffman/ansible-fact.svg?branch=dev)](https://travis-ci.com/ahuffman/ansible-fact)

## Table of Contents
<!-- TOC depthFrom:2 depthTo:6 withLinks:1 updateOnSave:1 orderedList:1 -->

1. [Table of Contents](#table-of-contents)
2. [About](#about)
3. [Available Modules](#available-modules)
4. [Module Documentation](#module-documentation)
5. [Contributions](#contributions)
	1. [Guidelines](#guidelines)
6. [Author(s)](#authors)

<!-- /TOC -->

## About
The concept of this project is to perform Infrastructure-as-Code in Reverse (i.e. iacir - pronounced: aya sir).  

`ansible-fact` consists of a collection of Ansible fact collectors to be able to generate structured data and harvest system configurations.  The goal is to be able to automatically collect all aspects of a system's configuration through modules.

## Available Modules
| Module Name | Description | Test Playbook |
| --- | --- | --- |
| [cron_facts](library/cron_facts.py) | Collects all cron data from a system and converts to structured data | [cron_facts.yml](test/cron_facts.yml) |
| [package_repository_facts](library/package_repository_facts.py) | Collects configured package repository data from a system and converts to structured data. | [package_repository_facts.yml](test/package_repository_facts.yml) |
| [process_facts](library/process_facts.py) | Collects currently running processes from a system and converts to structured data | [process_facts.yml](test/process_facts.yml) |
| [sudoers_facts](library/sudoers_facts.py) | Collects all sudoers configurations and converts to structured data | [sudoers_facts.yml](test/sudoers_facts.yml) |
| [user_group_facts](library/user_group_facts.py) | Collects all local user and group data from `/etc/shadow`, `/etc/gshadow`, `/etc/passwd`, and `/etc/group`, and merges into structured data. | [user_group_facts.yml](test/user_group_facts.yml)

## Module Documentation
All module documentation can be found in each respective module, as with any Ansible module.

## Contributions
Please feel free to openly contribute to this project.  All code will be reviewed prior to merging.

### Guidelines
* Please perform all development and pull requests against the `dev` branch of this repository.
* If a particular fact collector can apply to many different Operating Systems, please try and accommodate all Operating System implementations in an attempt to keep this project platform agnostic.
* Please include a test playbook in the [test](test) directory.
* Please place your modules in the [library](library) directory.
* Please document your code and modules thoroughly via comments and by following [Ansible's Module Development Documentation](https://docs.ansible.com/ansible/latest/dev_guide/developing_modules_general.html#starting-a-new-module).

## Author(s)
[Andrew J. Huffman](https://github.com/ahuffman)  
[John Westcott IV](https://github.com/john-westcott-iv)

## Licensing
Each module is licensed individually.  Please refer to the documentation header of each module for details.
