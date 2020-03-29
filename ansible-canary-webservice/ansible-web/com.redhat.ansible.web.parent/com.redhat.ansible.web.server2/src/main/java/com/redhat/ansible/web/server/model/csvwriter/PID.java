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
public class PID {

    @DataField(pos = 2)
    private String tty;
    @DataField(pos = 3)
    private String cpu_per;
    @DataField(pos = 4)
    private String pid;
    @DataField(pos = 5)
    private String mem_perc;
    @DataField(pos = 6)
    private String command;
    @DataField(pos = 7)
    private String user;
    @DataField(pos = 8)
    private String stat;

    public String getTty() {
        return tty;
    }

    public void setTty(String tty) {
        this.tty = tty;
    }

    public String getCpu_per() {
        return cpu_per;
    }

    public void setCpu_per(String cpu_per) {
        this.cpu_per = cpu_per;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMem_perc() {
        return mem_perc;
    }

    public void setMem_perc(String mem_perc) {
        this.mem_perc = mem_perc;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
