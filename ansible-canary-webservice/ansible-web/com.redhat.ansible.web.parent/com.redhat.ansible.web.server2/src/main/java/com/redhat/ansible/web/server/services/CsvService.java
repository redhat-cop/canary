/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.services;

import com.redhat.ansible.web.server.model.csvwriter.CsvCronJob;
import com.redhat.ansible.web.server.model.csvwriter.CsvGeneral;
import com.redhat.ansible.web.server.model.csvwriter.CsvGroups;
import com.redhat.ansible.web.server.model.csvwriter.CsvLVM;
import com.redhat.ansible.web.server.model.csvwriter.CsvListeners;
import com.redhat.ansible.web.server.model.csvwriter.CsvMounts;
import com.redhat.ansible.web.server.model.csvwriter.CsvNetworkInterfaces;
import com.redhat.ansible.web.server.model.csvwriter.CsvPackages;
import com.redhat.ansible.web.server.model.csvwriter.CsvProcesses;
import com.redhat.ansible.web.server.model.csvwriter.CsvRepoLists;
import com.redhat.ansible.web.server.model.csvwriter.CsvServiceScripts;
import com.redhat.ansible.web.server.model.csvwriter.CsvUsers;
import com.redhat.ansible.web.server.model.csvwriter.Group;
import com.redhat.ansible.web.server.model.csvwriter.LVS;
import com.redhat.ansible.web.server.model.csvwriter.Listener;
import com.redhat.ansible.web.server.model.csvwriter.Mount;
import com.redhat.ansible.web.server.model.csvwriter.NetworkInterface;
import com.redhat.ansible.web.server.model.csvwriter.PID;
import com.redhat.ansible.web.server.model.csvwriter.RPM;
import com.redhat.ansible.web.server.model.csvwriter.RepoList;
import com.redhat.ansible.web.server.model.csvwriter.ServiceScript;
import com.redhat.ansible.web.server.model.csvwriter.User;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfreese
 */
@Named("CsvService")
public class CsvService {

    public static final Logger LOG = LoggerFactory.getLogger(CsvService.class);

    public CsvGeneral generalFactsCsv(Map<String, Object> data) {

        CsvGeneral general = new CsvGeneral();
        general.setHostname((String) data.get("hostname"));

        Map<String, Object> data_facts = (Map<String, Object>) data.get("facts");
        Map<String, Object> ansible_env = (Map<String, Object>) data_facts.get("ansible_env");
        Map<String, Object> ansible_seLinux = (Map<String, Object>) data_facts.get("ansible_selinux");
        Map<String,Object> default_ipv4_facts = (Map<String,Object>) data_facts.get("ansible_default_ipv4"); 

        general.setProduct((String) data_facts.get("ansible_product_name"));
        general.setDistribution((String) data_facts.get("ansible_distribution_version")); //this is truncating the 5.11 label, no good 
        general.setDomain((String) data_facts.get("ansible_domain"));
        general.setOs_family((String) data_facts.get("ansible_os_family"));
        general.setProcessor_cores((Integer) data_facts.get("ansible_processor_cores"));
        general.setPath((String) ansible_env.get("PATH"));
        general.setArchitecture((String) data_facts.get("ansible_architecture"));
        general.setKernel((String) data_facts.get("ansible_kernel"));
        general.setFdqn((String) data_facts.get("ansible_fqdn"));
        general.setService_manager((String) data_facts.get("ansible_service_mgr"));
        general.setMem_total((Integer) data_facts.get("ansible_memtotal_mb"));
        general.setIpv4((String) default_ipv4_facts.get("address"));
        
        String selinux_status = (String) ansible_seLinux.get("status"); 
        if(selinux_status != null){
            general.setSeLinux(selinux_status);
        }else{
            general.setSeLinux((String) ansible_seLinux.get("mode"));
        }

        return general;

    }

    public CsvLVM lvmFactsCsv(Map<String, Object> data) {

        CsvLVM lvm = new CsvLVM();
        lvm.setHostname((String) data.get("hostname"));

        Map<String, Object> data_facts = (Map<String, Object>) data.get("facts");
        if(data_facts.get("ansible_lvm") != null){
            
            Map<String, Object> lvmData = (Map<String, Object>) data_facts.get("ansible_lvm");
            Map<String, Object> lvsData = (Map<String, Object>) lvmData.get("lvs");

            for (String key : lvsData.keySet()) {
                LVS lvs = new LVS();
                lvs.setName(key);
                Map<String, Object> lvsObject = (Map<String, Object>) lvsData.get(key);

                lvs.setSize((String) lvsObject.get("size_g"));
                lvs.setVolume_group((String) lvsObject.get("vg"));
                lvm.getLvs().add(lvs);
            }
        }

        return lvm;

    }

    public CsvMounts mountFactsCsv(Map<String, Object> data) {

        CsvMounts mounts = new CsvMounts();

        mounts.setHostname((String) data.get("hostname"));
        Map<String, Object> data_facts = (Map<String, Object>) data.get("facts");

        List<Map<String, Object>> mountsData = (List<Map<String, Object>>) data_facts.get("ansible_mounts");

        for (Map<String, Object> mount : mountsData) {

            Mount csvMount = new Mount();
            csvMount.setUuid((String) mount.get("uuid"));
            csvMount.setMount((String) mount.get("mount"));

            
            if (mount.get("size_total") instanceof Integer) {
                Integer value = (Integer) mount.get("size_total");
                Long corrected_valued = new Long(value);
                csvMount.setSize_total( corrected_valued );
            } else {
                Long value = new Long ((Long) mount.get("size_total")); 
                csvMount.setSize_total(value);
            }

            if (mount.get("size_available") instanceof Integer) {
                Integer value = (Integer) mount.get("size_available");
                Long corrected_valued = new Long(value);
                csvMount.setSize_available(corrected_valued);
            } else {
                Long value = new Long ((Long) mount.get("size_available")); 
                csvMount.setSize_available(value);
            }

            csvMount.setFstype((String) mount.get("fstype"));
            csvMount.setDevice((String) mount.get("device"));
            csvMount.setPrivileges((String) mount.get("options"));

            mounts.getMounts().add(csvMount);

        }

        return mounts;

    }

    public CsvNetworkInterfaces nicFactsCsv(Map<String, Object> data) {

        CsvNetworkInterfaces nics = new CsvNetworkInterfaces();

        nics.setHostname((String) data.get("hostname"));
        Map<String, Object> data_facts = (Map<String, Object>) data.get("facts");
        List<String> interfaces = (List<String>) data_facts.get("ansible_interfaces");

        Map<String, String> default_ipv4 = (Map<String, String>) data_facts.get("ansible_default_ipv4");
        NetworkInterface nic_default = new NetworkInterface();

        nic_default.setInterface_type(default_ipv4.get("interface"));
        nic_default.setAddress(default_ipv4.get("address"));
        nic_default.setNetwork(default_ipv4.get("network"));
        nic_default.setNetmask(default_ipv4.get("netmask"));
        nic_default.setGateway(default_ipv4.get("gateway"));
        nics.getNics().add(nic_default);

        for (String type : interfaces) {
            
            String nic_key = "ansible_" + type;
            
            if (!type.equals(nic_default.getInterface_type()) & data_facts.containsKey(nic_key)) {

                NetworkInterface nic = new NetworkInterface();

                nic.setInterface_type(type);
                    Map<String, Object> nic_data = (Map<String, Object>) data_facts.get(nic_key);
                    
                    if(nic_data.containsKey("ipv4")){
                        Map<String, String> nic_data_ipv4 = (Map<String, String>) nic_data.get("ipv4");

                        nic.setAddress(nic_data_ipv4.get("address"));
                        nic.setNetwork(nic_data_ipv4.get("network"));
                        nic.setNetmask(nic_data_ipv4.get("netmask"));                
                        
                    }
                        nics.getNics().add(nic);
            }

        }

        return nics;

    }
    
    public CsvListeners listenerFactsCsv(Map<String, Object> data) {

        List<Map<String, Object>> listeners_facts = (List<Map<String, Object>>) data.get("listeners");

        CsvListeners csvListeners = new CsvListeners();
        csvListeners.setHostname((String) data.get("hostname"));
        
        for (Map<String, Object> facts : listeners_facts) {
            Listener lis = new Listener();
            lis.setProtocol((String) facts.get("protocol"));
            lis.setLocal_address((String) facts.get("local_address"));
            lis.setLocal_port((String) facts.get("local_port"));
            lis.setForeign_address((String) facts.get("foreign_address"));
            lis.setForeign_port((String) facts.get("foreign_port"));
            lis.setState((String) facts.get("state"));
            lis.setPid((String) facts.get("pid"));
            lis.setProcess((String) facts.get("process"));

            csvListeners.getListeners().add(lis);
        }

        return csvListeners;

    }
    
    public CsvRepoLists repolistFactsCsv(Map<String,Object> data) {
        
        List<Map<String,Object>> repo_list_facts = (List<Map<String,Object>>) data.get("repo_list"); 
        
        CsvRepoLists csvRepoList = new CsvRepoLists(); 
        csvRepoList.setHostname((String) data.get(("hostname")));
        
        for(Map<String,Object> facts: repo_list_facts){
            if (facts != null){
                RepoList repolist = new RepoList(); 
                repolist.setRepoId((String) facts.get("repo_id"));
                repolist.setRepoName((String) facts.get("repo_name"));
                repolist.setRepoStatus((String) facts.get("repo_status"));

                csvRepoList.getRepolist().add(repolist);                 
            }
        }
        return csvRepoList;
        
    }
    
    public CsvProcesses processesFactsCsv(Map<String, Object> data) {
        List<Map<String, Object>> process_facts = (List<Map<String, Object>>) data.get("processes");

        CsvProcesses csvProcesses = new CsvProcesses();
        csvProcesses.setHostname((String) data.get("hostname"));

        for (Map<String, Object> facts : process_facts) {
            PID process = new PID();
            process.setTty((String) facts.get("TTY"));
            process.setCpu_per((String) facts.get("CPU_Perc"));
            process.setPid((String) facts.get("PID"));
            process.setMem_perc((String) facts.get("MEM_Perc"));
            process.setCommand((String) facts.get("COMMAND"));
            process.setUser((String) facts.get("USER"));
            process.setStat((String) facts.get("STAT"));

            csvProcesses.getProceses().add(process);

        }

        return csvProcesses;

    }
    
    public CsvUsers userFactsCsv(Map<String, Object> data) {
        Map<String, Object> users_groups = (Map<String, Object>) data.get("user_group");
        List<Map<String, Object>> users = (List<Map<String, Object>>) users_groups.get("users");

        CsvUsers csvUsers = new CsvUsers();
        csvUsers.setHostname((String) data.get("hostname"));
        for (Map<String, Object> facts : users) {
            User user = new User();
            user.setUsername((String) facts.get("username"));
            user.setHome_directory((String) facts.get("home_directory"));
            user.setLogin_shell((String) facts.get("login_shell"));
            user.setUid((String) facts.get("uid"));
            user.setEncrypted_password((String) facts.get("encrypted_password"));

            csvUsers.getUsers().add(user);
        }

        return csvUsers;

    }
    
    public CsvGroups groupFactsCsv(Map<String, Object> data) {
        Map<String, Object> users_groups = (Map<String, Object>) data.get("user_group");
        List<Map<String, Object>> groups = (List<Map<String, Object>>) users_groups.get("groups");

        CsvGroups csvGroups = new CsvGroups();
        csvGroups.setHostname((String) data.get("hostname"));

        for (Map<String, Object> facts : groups) {
            Group group = new Group();
            group.setGroup_name((String) facts.get("group_name"));
            group.setGid((String)facts.get("gid"));
            group.setGroup_list((String) facts.get("group_list"));

            csvGroups.getGroups().add(group);
        }

        return csvGroups;
    }
    
    public CsvCronJob cronJobFactsCsv(Map<String,Object> data){
        Map<String,Object> cron_jobs = (Map<String,Object>) data.get("cron_jobs");
     
        CsvCronJob csvCron = new CsvCronJob(); 
        csvCron.setHostname((String) data.get("hostname"));
        
        StringBuilder cronfiles = new StringBuilder(); 
        StringBuilder crontab= new StringBuilder(); 
        StringBuilder varSpool= new StringBuilder(); 
        
        List<String> cronfile_facts = (List<String>) cron_jobs.get("cronfiles"); 
        if(cronfile_facts != null){
            
            cronfiles.append(cronfile_facts.stream().collect(Collectors.joining("|"))); 
        }
        
       List<Map<String,Object>> crontab_facts = (List<Map<String,Object>>) cron_jobs.get("crontab"); 
       if(crontab_facts != null){
            crontab_facts.forEach( r ->{
                crontab.append(r.get("minute") + " " + r.get("hour") + " " + r.get("day") + " " +r.get("month") 
                        + " " + r.get("weekday") +" " + r.get("user") + " " + r.get("command") + " | ");
           });           
       }
       
       List<String> varspool_facts = (List<String>) cron_jobs.get("var_spool"); 
       if(varspool_facts != null){
           varSpool.append(varspool_facts.stream().collect(Collectors.joining("|"))); 
       }
       
       csvCron.setCronFiles(cronfiles.toString());
       csvCron.setCrontab(crontab.toString());
       csvCron.setVarSpool(varSpool.toString());
        
        return csvCron; 
    }
    
    public CsvPackages packageFactsCsv(Map<String,Object> data){
        
        List<Map<String,Object>> package_facts = (List<Map<String,Object>>) data.get("packages"); 
        CsvPackages packages = new CsvPackages(); 
        
        packages.setHostname((String)data.get("hostname"));
        for (Map<String,Object> facts: package_facts){
            if (facts != null){
                RPM pac = new RPM(); 
                pac.setName((String) facts.get("name"));
                pac.setSource((String)facts.get("rpm"));
                pac.setEpoch((Integer)facts.get("epoch"));
                pac.setVersion((String)facts.get("version"));
                pac.setRelease((String)facts.get("release"));
                pac.setArch((String)facts.get("arch"));
                packages.getPackages().add(pac);
            }

        }
        
        return packages; 
    }
    
    public CsvServiceScripts serviceFactsCsv(Map<String,Object> data){
        List<Map<String,Object>> service_facts = (List<Map<String,Object>>) data.get("services");  
        CsvServiceScripts services = new CsvServiceScripts(); 
        
        services.setHostname((String)data.get("hostname"));
        for(Map<String,Object> facts: service_facts){
            if (facts != null){
                ServiceScript service = new ServiceScript(); 
                service.setName((String)facts.get("name"));
                service.setState((String)facts.get("state"));
                service.setSource((String)facts.get("source"));
                services.getServices().add(service); 
            }
            
        }
        
        return services; 
    }
    
    

}
