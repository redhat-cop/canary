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
public class ApplicationDefinition {

    private Integer id;
    
    private String app_name;
    
    private String app_type;
        
    private Boolean docker_target;
    private Boolean cloud_target;
    private Boolean vm_target;
    private Integer impact_level;
    private String vendor;
    private String major_version;
    private String minor_version;
    private String support_type;
    private String support_status;
    private String supported_linux_distributions;
    private String downstream_dependencies;
    private String upstream_dependencies;
    private Integer parent_app_id;

    // extra variable to look up parent_app_id; 
    private String parent_app_name; 
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApp_name() {
        return app_name;
    }

    public void setApp_name(String app_name) {
        this.app_name = app_name;
    }

    public String getApp_type() {
        return app_type;
    }

    public void setApp_type(String app_type) {
        this.app_type = app_type;
    }

    public Boolean getDocker_target() {
        return docker_target;
    }

    public void setDocker_target(Boolean docker_target) {
        this.docker_target = docker_target;
    }

    public Boolean getCloud_target() {
        return cloud_target;
    }

    public void setCloud_target(Boolean cloud_target) {
        this.cloud_target = cloud_target;
    }

    public Boolean getVm_target() {
        return vm_target;
    }

    public void setVm_target(Boolean vm_target) {
        this.vm_target = vm_target;
    }

    public Integer getImpact_level() {
        return impact_level;
    }

    public void setImpact_level(Integer impact_level) {
        this.impact_level = impact_level;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getMajor_version() {
        return major_version;
    }

    public void setMajor_version(String major_version) {
        this.major_version = major_version;
    }

    public String getMinor_version() {
        return minor_version;
    }

    public void setMinor_version(String minor_version) {
        this.minor_version = minor_version;
    }

    public String getSupport_type() {
        return support_type;
    }

    public void setSupport_type(String support_type) {
        this.support_type = support_type;
    }

    public String getSupport_status() {
        return support_status;
    }

    public void setSupport_status(String support_status) {
        this.support_status = support_status;
    }

    public String getSupported_linux_distributions() {
        return supported_linux_distributions;
    }

    public void setSupported_linux_distributions(String supported_linux_distribution) {
        this.supported_linux_distributions = supported_linux_distribution;
    }

    public String getDownstream_dependencies() {
        return downstream_dependencies;
    }

    public void setDownstream_dependencies(String downstream_dependencies) {
        this.downstream_dependencies = downstream_dependencies;
    }

    public String getUpstream_dependencies() {
        return upstream_dependencies;
    }

    public void setUpstream_dependencies(String upstream_dependencies) {
        this.upstream_dependencies = upstream_dependencies;
    }

    public Integer getParent_app_id() {
        return parent_app_id;
    }

    public void setParent_app_id(Integer parent_app_id) {
        this.parent_app_id = parent_app_id;
    }

    public String getParent_app_name() {
        return parent_app_name;
    }

    public void setParent_app_name(String parent_app_name) {
        this.parent_app_name = parent_app_name;
    }

}
