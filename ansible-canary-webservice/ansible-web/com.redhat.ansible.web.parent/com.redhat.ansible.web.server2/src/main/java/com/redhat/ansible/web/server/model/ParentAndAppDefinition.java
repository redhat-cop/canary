/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model;

import java.util.List;

/**
 *
 * @author dfreese
 */
public class ParentAndAppDefinition {
   
    public ParentApp parentApp; 
    public List<ApplicationDefinition> app_list; 

    public ParentApp getParentApp() {
        return parentApp;
    }

    public void setParentApp(ParentApp parentApp) {
        this.parentApp = parentApp;
    }

    public List<ApplicationDefinition> getApp_list() {
        return app_list;
    }

    public void setApp_list(List<ApplicationDefinition> app_list) {
        this.app_list = app_list;
    }
    
    
    
}
