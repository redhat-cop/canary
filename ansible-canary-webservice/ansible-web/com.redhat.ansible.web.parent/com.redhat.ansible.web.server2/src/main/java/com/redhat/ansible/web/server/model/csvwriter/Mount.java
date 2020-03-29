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
public class Mount {

    @DataField(pos = 2)
    private String uuid;
    @DataField(pos = 3)
    private String mount;
    @DataField(pos = 4)
    private Long size_total;
    @DataField(pos = 5)
    private Long size_available;
    @DataField(pos = 6)
    private String fstype;
    @DataField(pos = 7)
    private String device;
    @DataField(pos = 8)
    private String privileges;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getSize_total() {
        return size_total;
    }

    public void setSize_total(Long size_total) {
        this.size_total = size_total;
    }

    public Long getSize_available() {
        return size_available;
    }

    public void setSize_available(Long size_available) {
        this.size_available = size_available;
    }

    public String getMount() {
        return mount;
    }

    public void setMount(String mount) {
        this.mount = mount;
    }

    public String getFstype() {
        return fstype;
    }

    public void setFstype(String fstype) {
        this.fstype = fstype;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String privileges) {
        this.privileges = privileges;
    }

}
