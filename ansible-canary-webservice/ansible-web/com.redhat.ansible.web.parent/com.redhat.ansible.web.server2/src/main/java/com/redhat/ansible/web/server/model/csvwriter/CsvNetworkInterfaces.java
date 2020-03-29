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
public class CsvNetworkInterfaces {

    @DataField(pos = 1)
    private String hostname;

    @OneToMany
    private List<NetworkInterface> nics;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public List<NetworkInterface> getNics() {
        if (nics == null) {
            nics = new ArrayList();
        }
        return nics;
    }

    public void setNics(List<NetworkInterface> nics) {
        this.nics = nics;
    }

}
