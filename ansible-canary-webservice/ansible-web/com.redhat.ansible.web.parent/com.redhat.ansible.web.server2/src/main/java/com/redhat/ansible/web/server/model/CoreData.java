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
public class CoreData {

    private String hostname;
    private int id; 
    private int server_id; 
    private String ipv4_addr;
    private String dest_hostname;
    private String dest_ipv4_addr;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServer_id() {
        return server_id;
    }

    public void setServer_id(int server_id) {
        this.server_id = server_id;
    }

    public String getIpv4_addr() {
        return ipv4_addr;
    }

    public void setIpv4_addr(String ipv4_addr) {
        this.ipv4_addr = ipv4_addr;
    }

    public String getDest_hostname() {
        return dest_hostname;
    }

    public void setDest_hostname(String dest_hostname) {
        this.dest_hostname = dest_hostname;
    }

    public String getDest_ipv4_addr() {
        return dest_ipv4_addr;
    }

    public void setDest_ipv4_addr(String dest_ipv4_addr) {
        this.dest_ipv4_addr = dest_ipv4_addr;
    }

}
