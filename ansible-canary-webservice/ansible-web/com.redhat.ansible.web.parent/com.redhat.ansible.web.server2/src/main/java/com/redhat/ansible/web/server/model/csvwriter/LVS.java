/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csvwriter;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

/**
 *
 * @author dfreese
 */
@Link
public class LVS {
    
    @DataField(pos = 2)
    private String name;
    @DataField(pos = 3)
    private String size;
    @DataField(pos = 4)
    private String volume_group;  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getVolume_group() {
        return volume_group;
    }

    public void setVolume_group(String volume_group) {
        this.volume_group = volume_group;
    }



}
