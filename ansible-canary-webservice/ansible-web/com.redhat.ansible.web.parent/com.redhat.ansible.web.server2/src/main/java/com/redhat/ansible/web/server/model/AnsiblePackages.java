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
public class AnsiblePackages extends CoreData {

    @JsonRawValue
    private String packages;

    @JsonRawValue
    private String dest_packages;

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getDest_packages() {
        return dest_packages;
    }

    public void setDest_packages(String dest_packages) {
        this.dest_packages = dest_packages;
    }

}
