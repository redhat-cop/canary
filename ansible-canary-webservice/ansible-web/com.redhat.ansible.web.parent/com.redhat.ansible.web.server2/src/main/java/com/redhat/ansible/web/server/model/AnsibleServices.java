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
public class AnsibleServices extends CoreData {

    @JsonRawValue
    private String services;

    @JsonRawValue
    private String dest_services;

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getDest_services() {
        return dest_services;
    }

    public void setDest_services(String dest_services) {
        this.dest_services = dest_services;
    }

}
