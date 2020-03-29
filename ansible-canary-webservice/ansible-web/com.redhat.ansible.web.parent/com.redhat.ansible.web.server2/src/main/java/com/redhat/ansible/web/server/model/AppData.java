/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model;

/**
 *
 * @author dfreese
 */
public class AppData {
    
    private int id; 
    private int appcode; 
    private String parent_app_name; 
    private String hostname; 
    private String fqdn;
    private String sat_ipv4_addr; 
    private String services; 
    private String environments; 
    private String migration_status;    
    private String destination_host; 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppcode() {
        return appcode;
    }

    public void setAppcode(int appcode) {
        this.appcode = appcode;
    }

    public String getParent_app_name() {
        return parent_app_name;
    }

    public void setParent_app_name(String parent_app_name) {
        this.parent_app_name = parent_app_name;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getFqdn() {
        return fqdn;
    }

    public void setFqdn(String fqdn) {
        this.fqdn = fqdn;
    }

    public String getSat_ipv4_addr() {
        return sat_ipv4_addr;
    }

    public void setSat_ipv4_addr(String sat_ipv4_addr) {
        this.sat_ipv4_addr = sat_ipv4_addr;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getEnvironments() {
        return environments;
    }

    public void setEnvironments(String environments) {
        this.environments = environments;
    }

    public String getMigration_status() {
        return migration_status;
    }

    public void setMigration_status(String migration_status) {
        this.migration_status = migration_status;
    }

    public String getDestination_host() {
        return destination_host;
    }

    public void setDestination_host(String destination_host) {
        this.destination_host = destination_host;
    }
    
    
}
