/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.ansible.web.server.model.AnsibleData;
import java.util.Map;

/**
 *
 * @author dfreese
 */
public class DataConversions {

    public static AnsibleData convertMapToAnsibleData(Map<String, Object> data, String hostname) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        String facts = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("facts"));
        String services = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("services"));
        String packages = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("packages"));
        String listeners = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("listeners"));
        String cron_jobs = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("cron_jobs"));
        String repo_list = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("repo_list"));
        String users_groups = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("user_group"));
        String processes = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(data.get("processes"));

        AnsibleData ansibleData = new AnsibleData();
        ansibleData.setHostname(hostname); 
        ansibleData.setFacts(facts);
        ansibleData.setServices(services);
        ansibleData.setPackages(packages);
        ansibleData.setListeners(listeners);
        ansibleData.setCron_jobs(cron_jobs);
        ansibleData.setRepo_list(repo_list);
        ansibleData.setUser_group(users_groups);
        ansibleData.setProcesses(processes);

        return ansibleData;

    }
    
}
