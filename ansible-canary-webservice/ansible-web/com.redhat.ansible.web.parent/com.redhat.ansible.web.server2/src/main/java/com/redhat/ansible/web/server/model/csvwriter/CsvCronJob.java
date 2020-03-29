/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csvwriter;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 *
 * @author dfreese
 */
@CsvRecord(separator = ",")
public class CsvCronJob {
    
    @DataField(pos = 1)
    private String hostname; 
    @DataField(pos = 2)
    private String cronFiles;
    @DataField(pos = 3)
    private String crontab;
    @DataField(pos = 4)
    private String varSpool;

    
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getCronFiles() {
        return cronFiles;
    }

    public void setCronFiles(String cronFiles) {
        this.cronFiles = cronFiles;
    }

    public String getCrontab() {
        return crontab;
    }

    public void setCrontab(String crontab) {
        this.crontab = crontab;
    }

    public String getVarSpool() {
        return varSpool;
    }

    public void setVarSpool(String varSpool) {
        this.varSpool = varSpool;
    }

}
