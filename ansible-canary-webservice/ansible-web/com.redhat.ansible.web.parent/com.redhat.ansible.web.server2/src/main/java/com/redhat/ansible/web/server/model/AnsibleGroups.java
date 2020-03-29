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
public class AnsibleGroups extends CoreData {

    @JsonRawValue
    private String groups;

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

}
