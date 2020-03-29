/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csv;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

/**
 *
 * @author dfreese
 */
@Link
public class ServerCsv {

    private Integer id;

    @DataField(pos = 8)
    private String environment;

    @DataField(pos = 9)
    private String hostname;

    @DataField(pos = 10)
    private String fqdn;

    @DataField(pos = 11)
    private String dns_ip;

    @DataField(pos = 12)
    private Boolean dns_ping;

    @DataField(pos = 13)
    private String sat_name;

    @DataField(pos = 14)
    private String sat_ipv4_addr;

    @DataField(pos = 15)
    private Boolean sat_ping;

    @DataField(pos = 16)
    private Integer rhel_version;

    @DataField(pos = 17)
    private String notes;

    @DataField(pos = 5)
    private String services;

    private String domainName;
    private String macAddress;

    private Integer parent_app_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
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

    public String getDns_ip() {
        return dns_ip;
    }

    public void setDns_ip(String dns_ip) {
        this.dns_ip = dns_ip;
    }

    public Boolean getDns_ping() {
        return dns_ping;
    }

    public void setDns_ping(Boolean dns_ping) {
        this.dns_ping = dns_ping;
    }

    public String getSat_name() {
        return sat_name;
    }

    public void setSat_name(String sat_name) {
        this.sat_name = sat_name;
    }

    public String getSat_ipv4_addr() {
        return sat_ipv4_addr;
    }

    public void setSat_ipv4_addr(String sat_ipv4_addr) {
        this.sat_ipv4_addr = sat_ipv4_addr;
    }

    public Boolean getSat_ping() {
        return sat_ping;
    }

    public void setSat_ping(Boolean sat_ping) {
        this.sat_ping = sat_ping;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Integer getRhel_version() {
        return rhel_version;
    }

    public void setRhel_version(Integer rhel_version) {
        this.rhel_version = rhel_version;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public Integer getParent_app_id() {
        return parent_app_id;
    }

    public void setParent_app_id(Integer parent_app_id) {
        this.parent_app_id = parent_app_id;
    }

}
