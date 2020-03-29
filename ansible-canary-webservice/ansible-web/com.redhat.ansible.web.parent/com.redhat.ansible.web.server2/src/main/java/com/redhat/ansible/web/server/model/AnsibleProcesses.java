/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 *
 * @author dfreese
 */
public class AnsibleProcesses extends CoreData {

    @JsonRawValue
    private String processes;

    @JsonRawValue
    private String dest_processes;

    public String getProcesses() {
        return processes;
    }

    public void setProcesses(String processes) {
        this.processes = processes;
    }

    public String getDest_processes() {
        return dest_processes;
    }

    public void setDest_processes(String dest_processes) {
        this.dest_processes = dest_processes;
    }

}
