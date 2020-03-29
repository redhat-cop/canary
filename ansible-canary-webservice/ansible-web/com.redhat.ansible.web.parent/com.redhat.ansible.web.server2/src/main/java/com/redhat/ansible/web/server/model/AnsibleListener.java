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
public class AnsibleListener extends CoreData {

    @JsonRawValue
    private String listeners;

    @JsonRawValue
    private String dest_listeners;

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getDest_listeners() {
        return dest_listeners;
    }

    public void setDest_listeners(String dest_listeners) {
        this.dest_listeners = dest_listeners;
    }

}
