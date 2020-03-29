/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csv;

import javax.inject.Named;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.Link;

/**
 *
 * @author dfreese
 */
@CsvRecord(separator = ",")
@Named("CsvServerImport")
public class CsvServerImport {

    @Link
    ParentAppCsv parentAppCsv;


    @Link
    ServerCsv serverCsv;
    
    @Link 
    OwnershipCsv ownershipCsv; 

    public ParentAppCsv getParentAppCsv() {
        return parentAppCsv;
    }

    public void setParentAppCsv(ParentAppCsv parentAppCsv) {
        this.parentAppCsv = parentAppCsv;
    }

    public ServerCsv getServerCsv() {
        return serverCsv;
    }

    public void setServerCsv(ServerCsv serverCsv) {
        this.serverCsv = serverCsv;
    }

    public OwnershipCsv getOwnershipCsv() {
        return ownershipCsv;
    }

    public void setOwnershipCsv(OwnershipCsv ownershipCsv) {
        this.ownershipCsv = ownershipCsv;
    }
    
    

}
