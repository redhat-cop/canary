/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model;

import com.fasterxml.jackson.annotation.JsonRawValue;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dfreese
 */
public class Facts {
    private String ansible_distribution_version;

    private String ansible_processor_cores;

    private String ansible_memtotal_mb;

    private String ansible_architecture;

    private String ansible_kernel;

    @JsonRawValue
    private String ansible_mounts;

    private List<AnsibleLVM> ansible_lvm;

    public String getAnsible_distribution_version() {
        return ansible_distribution_version;
    }

    public void setAnsible_distribution_version(String ansible_distribution_version) {
        this.ansible_distribution_version = ansible_distribution_version;
    }

    public String getAnsible_processor_cores() {
        return ansible_processor_cores;
    }

    public void setAnsible_processor_cores(String ansible_processor_cores) {
        this.ansible_processor_cores = ansible_processor_cores;
    }

    public String getAnsible_memtotal_mb() {
        return ansible_memtotal_mb;
    }

    public void setAnsible_memtotal_mb(String ansible_memtotal_mb) {
        this.ansible_memtotal_mb = ansible_memtotal_mb;
    }

    public String getAnsible_architecture() {
        return ansible_architecture;
    }

    public void setAnsible_architecture(String ansible_architecture) {
        this.ansible_architecture = ansible_architecture;
    }

    public String getAnsible_kernel() {
        return ansible_kernel;
    }

    public void setAnsible_kernel(String ansible_kernel) {
        this.ansible_kernel = ansible_kernel;
    }

    public String getAnsible_mounts() {
        return ansible_mounts;
    }

    public void setAnsible_mounts(String ansible_mounts) {
        this.ansible_mounts = ansible_mounts;
    }

    public List<AnsibleLVM> getAnsible_lvm() {
        if (ansible_lvm == null) {
            return new ArrayList<>();
        }

        return ansible_lvm;
    }

    public void setAnsible_lvm(List<AnsibleLVM> ansible_lvm) {
        this.ansible_lvm = ansible_lvm;
    }

    
}
