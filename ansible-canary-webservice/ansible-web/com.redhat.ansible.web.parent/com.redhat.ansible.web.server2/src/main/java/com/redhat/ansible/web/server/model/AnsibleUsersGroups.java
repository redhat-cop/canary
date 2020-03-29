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
public class AnsibleUsersGroups extends CoreData {

    @JsonRawValue
    private String users;
    @JsonRawValue
    private String dest_users;
    @JsonRawValue
    private String groups;
    @JsonRawValue
    private String dest_groups;

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getDest_users() {
        return dest_users;
    }

    public void setDest_users(String dest_users) {
        this.dest_users = dest_users;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getDest_groups() {
        return dest_groups;
    }

    public void setDest_groups(String dest_groups) {
        this.dest_groups = dest_groups;
    }

}
