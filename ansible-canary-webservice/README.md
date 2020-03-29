### Ansible Discovery Front End

#### Introduction
This is the Ansible Discovery UI developed customized for a RHEL Migration Project.

The front end is written in angular 5, and deployed in a Docker container. The front end code is located in the ansible-web-front directory. The documentation for the rest endpoints is located in the ansible-web-data.

The application is constructed with two Dockerfiles. A Dockerfile was designed to create node-sass-alpine image. This image provide the Sass binary to the node docker image.

The Dockerfile located in the ansible-web-front/Dockerfile is used to create the front-end deployment build.
#### Directory Structure
1. [ansible-web-data](ansible-web-data/README.md): Rest Endpoint Documentatation  
2. [ansible-web-front](ansible-web-front/README.md): Angular 5 UI project  
3. [node-sass-alpine Dockerfile](ansible-web-front/node-sass-docker/Dockerfile): node-sass-alpine image build
4. [ansibl-front Dockerfile](ansible-web-front/Dockerfile): ansible-front image build
