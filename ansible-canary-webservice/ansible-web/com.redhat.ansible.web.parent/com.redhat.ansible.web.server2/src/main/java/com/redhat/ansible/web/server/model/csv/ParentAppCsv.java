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
public class ParentAppCsv {

    @DataField(pos = 2)
    private Integer eai_code;

    @DataField(pos = 3)
    private String parent_app_name;

    @DataField(pos = 4)
    private Boolean scope; 

    private Integer id;

    public String getParent_app_name() {
        return parent_app_name;
    }

    public void setParent_app_name(String parent_app_name) {
        this.parent_app_name = parent_app_name;
    }

    public Integer getEai_code() {
        return eai_code;
    }

    public void setEai_code(Integer eai_code) {
        this.eai_code = eai_code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getScope() {
        return scope;
    }

    public void setScope(Boolean scope) {
        this.scope = scope;
    }
    
    
}
