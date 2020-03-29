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
public class ServerDefinition {

    private Integer id;
    private String hostname; 
    private String fqdn; 
    private String vmware_name; 
    private String domainName; 
    private String mac_address; 
    private String sat_ipv4_addr; 
    private String sat_name; 
    private String dns_ipv4_addr; 
    private String rhel_version; 
    private String environment; 
    private String app_id; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getVmware_name() {
        return vmware_name;
    }

    public void setVmware_name(String vmware_name) {
        this.vmware_name = vmware_name;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getSat_ipv4_addr() {
        return sat_ipv4_addr;
    }

    public void setSat_ipv4_addr(String sat_ipv4_addr) {
        this.sat_ipv4_addr = sat_ipv4_addr;
    }

    public String getSat_name() {
        return sat_name;
    }

    public void setSat_name(String sat_name) {
        this.sat_name = sat_name;
    }

    public String getDns_ipv4_addr() {
        return dns_ipv4_addr;
    }

    public void setDns_ipv4_addr(String dns_ipv4_addr) {
        this.dns_ipv4_addr = dns_ipv4_addr;
    }

    public String getRhel_version() {
        return rhel_version;
    }

    public void setRhel_version(String rhel_version) {
        this.rhel_version = rhel_version;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }



}
