/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.redhat.ansible.web.server.model.AnsibleData;
import com.redhat.ansible.web.server.model.AnsibleFacts;
import com.redhat.ansible.web.server.model.AnsibleGroups;
import com.redhat.ansible.web.server.model.AnsibleListener;
import com.redhat.ansible.web.server.model.AnsiblePackages;
import com.redhat.ansible.web.server.model.AnsibleProcesses;
import com.redhat.ansible.web.server.model.AnsibleServices;
import com.redhat.ansible.web.server.model.AnsibleUsers;
import com.redhat.ansible.web.server.model.AppCode;
import com.redhat.ansible.web.server.model.AppData;
import com.redhat.ansible.web.server.model.Hostname;
import com.redhat.ansible.web.server.model.csv.CsvQuestionnaireImport;
import com.redhat.ansible.web.server.model.csv.CsvServerImport;
import com.redhat.ansible.web.server.utility.DataConversions;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfreese
 */
@Named("DiscoveryService")
public class DiscoveryService {

    public static final Logger LOG = LoggerFactory.getLogger(DiscoveryService.class);

    @Inject
    DBService dbService; 

    ///POST ALL FACTS 
    public AnsibleData insertAnsibleData(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException, SQLException {

        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.insertAllAnsibleFacts(ansibleData);
    }
    
    //GET ALL FACTS TO CSV FOR BY PARENT APP 
    public List<Map<String,Object>> getAppAnisbleFactsCSV(@ Header("eai_code") Integer eai_code){
        
        return dbService.getAppAnsibleFactsCSV(eai_code); 
          
    } 
    

    //ANSIBLE FACTS 
    public AnsibleData updateAnsibleFacts(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {

        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.updateAnsibleFacts(ansibleData);
    }

    public List<AnsibleFacts> retrieveAnsibleFacts(@Header("hostname") String hostname) {

        return dbService.retrieveAnsibleFacts(hostname);

    }

    //ANSIBLE SERVICES 
    public AnsibleData updateAnsibleServices(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {

        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.updateAnsibleServices(ansibleData);
    }

    public List<AnsibleServices> retrieveAnsibleServices(@Header("hostname") String hostname) throws JsonProcessingException {

        return dbService.retrieveAnsibleServices(hostname);
    }

    //ANSIBLE PACKAGES 
    public AnsibleData updateAnsiblePackages(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {
        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.updateAnsiblePackages(ansibleData);
    }

    public List<AnsiblePackages> retrieveAnsiblePackages(@Header("hostname") String hostname) throws JsonProcessingException {
        return dbService.retrieveAnsiblePackages(hostname);
    }

    //ANSIBLE LISTENERS 
    public AnsibleData updateAnsibleListeners(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {
        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.updateAnsibleListeners(ansibleData);
    }

    public List<AnsibleListener> retrieveAnsibleListeners(@Header("hostname") String hostname) throws JsonProcessingException {
        return dbService.retrieveAnsibleListeners(hostname); //need to correct the query to create the correct model 
    }

    //ANSIBLE CronJobs  
    public AnsibleData updateAnsibleCronjobs(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {
        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);

        return dbService.updateAnsibleCronJobs(ansibleData);
    }

    public AnsibleData retrieveAnsibleCronjobs(@Header("hostname") String hostname) throws JsonProcessingException {
        return dbService.retrieveAnsibleCronJobs(hostname);
    }

    //ANSIBLE REPO_LIST 
    public AnsibleData updateAnsibleRepoList(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {
        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.updateAnsibleRepoList(ansibleData);
    }

    public AnsibleData retrieveAnsibleRepoList(@Header("hostname") String hostname) throws JsonProcessingException {
        return dbService.retrieveAnsibleRepoList(hostname);
    }

    //ANSIBLE LOCAL USER GROUPS 
    public AnsibleData updateAnsibleUsersGroups(Map<String, Object> data, @Header("hostname") String hostname) throws JsonProcessingException {
        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname);
        return dbService.updateAnsibleUsersGroups(ansibleData);
    }
    
    public List<AnsibleUsers> retrieveAnsibleUsers(@Header("hostname") String hostname) throws JsonProcessingException {
        return dbService.retrieveAnsibleUsers(hostname); 
    }
    
    public List<AnsibleGroups> retrieveAnsibleGroups(@Header("hostname") String hostname) throws JsonProcessingException{
        return dbService.retrieveAnsibleGroups(hostname); 
    }
    
    //ANSIBLE PROCESSES 
    public AnsibleData updateAnsibleProcesses(Map<String,Object> data, @Header("hostname") String hostname ) throws JsonProcessingException {
        AnsibleData ansibleData = DataConversions.convertMapToAnsibleData(data, hostname); 
        return dbService.updateAnsibleProcesses(ansibleData); 
        
    }
    
    public List<AnsibleProcesses> retrieveAnsibleProcesses(@Header("hostname") String hostname){
        return dbService.retrieveAnsibleProcesses(hostname); 
    }
    
    public List<AppCode> retrieveAppCodes(){
        return dbService.retrieveAppCodes(); 
    }
    
    public List<AppData> retrieveAppData(@Header("appcode") int appcode){
        return dbService.retrieveAppData(appcode); 
    }
    
    public List<Hostname> retrieveServers(){
        return dbService.retrieveServers(); 
    }

    ///---------------CSV METHODS ------------------------------
    public void csvServerInsert(CsvServerImport data) {
        dbService.addCsvServerData(data);
    }

    public void csvServerUpdate(CsvServerImport data) {
        dbService.updateCSVServerData(data);
    }
    
    public void csvQuestionnaireInsert(List<CsvQuestionnaireImport> data){
        dbService.insertCsvQuestionnaireData(data);
    }
    
    public void csvQuestionnaireUpdate(List<CsvQuestionnaireImport> data){
        dbService.updateCsvQuestionnaireData(data);
    }

}
