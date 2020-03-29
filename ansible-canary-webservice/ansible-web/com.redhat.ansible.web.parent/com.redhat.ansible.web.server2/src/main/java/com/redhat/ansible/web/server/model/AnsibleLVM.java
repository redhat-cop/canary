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
public class AnsibleLVM {
    
    private String name; 
    private String size_g; 
    private String vg; 

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize_g() {
        return size_g;
    }

    public void setSize_g(String size_g) {
        this.size_g = size_g;
    }

    public String getVg() {
        return vg;
    }

    public void setVg(String vg) {
        this.vg = vg;
    }
    
    
    
}
