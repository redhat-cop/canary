/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 *
 * @author dfreese
 */
public class AnsibleData {

    private String hostname;
    private String ipv4;

    @JsonRawValue
    private String facts;
    
    @JsonRawValue
    private String services;
    
    @JsonRawValue
    private String packages;
    
    @JsonRawValue
    private String listeners; 

    @JsonRawValue
    private String cron_jobs; 

    @JsonRawValue
    private String repo_list; 
    
    @JsonRawValue
    private String user_group; 
    
    @JsonRawValue
    private String processes; 

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getFacts() {
        return facts;
    }

    public void setFacts(String facts) {
        this.facts = facts;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getCron_jobs() {
        return cron_jobs;
    }

    public void setCron_jobs(String cron_jobs) {
        this.cron_jobs = cron_jobs;
    }

    public String getRepo_list() {
        return repo_list;
    }

    public void setRepo_list(String repo_list) {
        this.repo_list = repo_list;
    }

    public String getUser_group() {
        return user_group;
    }

    public void setUser_group(String user_group) {
        this.user_group = user_group;
    }

    public String getProcesses() {
        return processes;
    }

    public void setProcesses(String processes) {
        this.processes = processes;
    }
    
    
}
