/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csv;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

/**
 *
 * @author dfreese
 */
@Link 
public class OwnershipCsv {
    
    private Integer id; 
    
    @DataField(pos = 1)
    private String financial_lob; 
    
    @DataField (pos = 7)
    private String project_manager; 

    @DataField(pos=6)
    private String business_owner; 
        
    private String source_code_owner; 
    private String avp; 
    private String bsa; 
    private String cio; 
    private String cre; 
    private String db_lead; 
    private String dba_lead; 
    private String director; 
    private String manager; 
    private String qa_lead; 
    private String sme; 
    private String tech_lead; 
    private String vp; 
    
    private String testing_team; 
    private String app_qa; 
    private Integer parent_app_id; 

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFinancial_lob() {
        return financial_lob;
    }

    public void setFinancial_lob(String financial_lob) {
        this.financial_lob = financial_lob;
    }

    public String getProject_manager() {
        return project_manager;
    }

    public void setProject_manager(String project_manager) {
        this.project_manager = project_manager;
    }

    public String getSource_code_owner() {
        return source_code_owner;
    }

    public void setSource_code_owner(String source_code_owner) {
        this.source_code_owner = source_code_owner;
    }

    public String getAvp() {
        return avp;
    }

    public void setAvp(String avp) {
        this.avp = avp;
    }

    public String getBsa() {
        return bsa;
    }

    public void setBsa(String bsa) {
        this.bsa = bsa;
    }

    public String getCio() {
        return cio;
    }

    public void setCio(String cio) {
        this.cio = cio;
    }

    public String getCre() {
        return cre;
    }

    public void setCre(String cre) {
        this.cre = cre;
    }

    public String getDb_lead() {
        return db_lead;
    }

    public void setDb_lead(String db_lead) {
        this.db_lead = db_lead;
    }

    public String getDba_lead() {
        return dba_lead;
    }

    public void setDba_lead(String dba_lead) {
        this.dba_lead = dba_lead;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getQa_lead() {
        return qa_lead;
    }

    public void setQa_lead(String qa_lead) {
        this.qa_lead = qa_lead;
    }

    public String getSme() {
        return sme;
    }

    public void setSme(String sme) {
        this.sme = sme;
    }

    public String getTech_lead() {
        return tech_lead;
    }

    public void setTech_lead(String tech_lead) {
        this.tech_lead = tech_lead;
    }

    public String getVp() {
        return vp;
    }

    public void setVp(String vp) {
        this.vp = vp;
    }

    public String getBusiness_owner() {
        return business_owner;
    }

    public void setBusiness_owner(String business_owner) {
        this.business_owner = business_owner;
    }

    public String getTesting_team() {
        return testing_team;
    }

    public void setTesting_team(String testing_team) {
        this.testing_team = testing_team;
    }

    public String getApp_qa() {
        return app_qa;
    }

    public void setApp_qa(String app_qa) {
        this.app_qa = app_qa;
    }

    public Integer getParent_app_id() {
        return parent_app_id;
    }

    public void setParent_app_id(Integer parent_app_id) {
        this.parent_app_id = parent_app_id;
    }


   
}
