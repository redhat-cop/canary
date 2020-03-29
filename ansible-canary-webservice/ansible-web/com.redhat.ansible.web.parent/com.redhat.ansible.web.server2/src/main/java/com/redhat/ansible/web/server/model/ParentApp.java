/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model;

/**
 *
 * @author dfreese
// */
public class ParentApp {
    
    private String parent_app_name;
    
    private Integer eai_code; 
    
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
    
}
