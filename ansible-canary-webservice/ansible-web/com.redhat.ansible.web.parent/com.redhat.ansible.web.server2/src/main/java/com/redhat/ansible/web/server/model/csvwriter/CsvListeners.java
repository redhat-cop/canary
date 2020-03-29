/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csvwriter;

import java.util.ArrayList;
import java.util.List;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.OneToMany;

/**
 *
 * @author dfreese
 */
@CsvRecord(separator = ",")
public class CsvListeners {

    @DataField(pos = 1)
    private String hostname;

    @OneToMany
    private List<Listener> listeners;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<Listener> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList();
        }
        return listeners;
    }

    public void setListeners(List<Listener> listeners) {
        this.listeners = listeners;
    }

}
