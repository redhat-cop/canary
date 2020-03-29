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
public class AnsibleServicesDefintion {
    private Integer id; 
    private String services; 
    private Integer server_id; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacts() {
        return services;
    }

    public void setFacts(String facts) {
        this.services = facts;
    }

    public Integer getServer_id() {
        return server_id;
    }

    public void setServer_id(Integer server_id) {
        this.server_id = server_id;
    }
}
