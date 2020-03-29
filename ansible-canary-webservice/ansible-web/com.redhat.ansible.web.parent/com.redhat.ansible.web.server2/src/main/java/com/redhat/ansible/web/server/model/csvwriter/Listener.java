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
public class Listener {

    @DataField(pos = 2)
    private String protocol;
    @DataField(pos = 3)
    private String local_address;
    @DataField(pos = 4)
    private String local_port;
    @DataField(pos = 5)
    private String foreign_address;
    @DataField(pos = 6)
    private String foreign_port;
    @DataField(pos = 7)
    private String state;
    @DataField(pos = 8)
    private String pid;
    @DataField(pos = 9)
    private String process;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getLocal_address() {
        return local_address;
    }

    public void setLocal_address(String local_address) {
        this.local_address = local_address;
    }

    public String getLocal_port() {
        return local_port;
    }

    public void setLocal_port(String local_port) {
        this.local_port = local_port;
    }

    public String getForeign_address() {
        return foreign_address;
    }

    public void setForeign_address(String foreign_address) {
        this.foreign_address = foreign_address;
    }

    public String getForeign_port() {
        return foreign_port;
    }

    public void setForeign_port(String foreign_port) {
        this.foreign_port = foreign_port;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }


}
