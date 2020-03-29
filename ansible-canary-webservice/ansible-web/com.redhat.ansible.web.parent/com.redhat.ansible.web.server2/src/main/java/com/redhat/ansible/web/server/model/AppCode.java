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
public class AppCode {
    
    private String app; 
    private int id; 
    private int appcode; 

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppcode() {
        return appcode;
    }

    public void setAppcode(int appcode) {
        this.appcode = appcode;
    }
    
}
