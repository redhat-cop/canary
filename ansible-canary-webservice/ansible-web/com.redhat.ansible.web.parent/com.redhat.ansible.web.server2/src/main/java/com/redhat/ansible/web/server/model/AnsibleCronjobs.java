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
public class AnsibleCronjobs extends CoreData {

    @JsonRawValue
    private String cron_jobs;
    @JsonRawValue
    private String dest_cron_jobs;

    public String getCron_jobs() {
        return cron_jobs;
    }

    public void setCron_jobs(String cron_jobs) {
        this.cron_jobs = cron_jobs;
    }

    public String getDest_cron_jobs() {
        return dest_cron_jobs;
    }

    public void setDest_cron_jobs(String dest_cron_jobs) {
        this.dest_cron_jobs = dest_cron_jobs;
    }

}
