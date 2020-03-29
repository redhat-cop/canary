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
public class CsvGeneral {

    @DataField(pos = 1)
    private String hostname;
    @DataField(pos = 2)
    private String fdqn;
    @DataField(pos = 3)
    private String ipv4;
    @DataField(pos = 4)
    private String domain;
    @DataField(pos = 5)
    private String os_family;
    @DataField(pos = 6)
    private String distribution_version;
    @DataField(pos = 7)
    private String product;
    @DataField(pos = 8)
    private Integer processor_cores;
    @DataField(pos = 9)
    private String path;
    @DataField(pos = 10)
    private String architecture;
    @DataField(pos = 11)
    private String kernel;
    @DataField(pos = 12)
    private String seLinux;
    @DataField(pos = 13)
    private String service_manager;
    @DataField(pos = 14)
    private Integer mem_total;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDistribution() {
        return distribution_version;
    }

    public void setDistribution(String distribution) {
        this.distribution_version = distribution;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getProcessor_cores() {
        return processor_cores;
    }

    public void setProcessor_cores(Integer processor_cores) {
        this.processor_cores = processor_cores;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String Path) {
        this.path = Path;
    }

    public String getArchitecture() {
        return architecture;
    }

    public void setArchitecture(String architecture) {
        this.architecture = architecture;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public String getFdqn() {
        return fdqn;
    }

    public void setFdqn(String fdqn) {
        this.fdqn = fdqn;
    }

    public String getSeLinux() {
        return seLinux;
    }

    public void setSeLinux(String seLinux) {
        this.seLinux = seLinux;
    }

    public String getDistribution_version() {
        return distribution_version;
    }

    public void setDistribution_version(String distribution_version) {
        this.distribution_version = distribution_version;
    }

    public String getService_manager() {
        return service_manager;
    }

    public void setService_manager(String service_manager) {
        this.service_manager = service_manager;
    }

    public String getOs_family() {
        return os_family;
    }

    public void setOs_family(String os_family) {
        this.os_family = os_family;
    }

    public Integer getMem_total() {
        return mem_total;
    }

    public void setMem_total(Integer mem_total) {
        this.mem_total = mem_total;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

}
