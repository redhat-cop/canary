/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redhat.ansible.web.server.model.AnsibleData;
import com.redhat.ansible.web.server.model.AnsibleFacts;
import com.redhat.ansible.web.server.model.AnsibleGroups;
import com.redhat.ansible.web.server.model.AnsibleLVM;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfreese
 */
@Named("DBService")
public class DBService {

    public static final Logger LOG = LoggerFactory.getLogger(DBService.class);

    private DataSource ds;

    @PostConstruct
    public void activate() {

        getDataSource();

    }

    public void getDataSource() {
        if (ds == null) {
            try {
                Context ctx = new InitialContext();
                ds = (DataSource) ctx.lookup(("java:jboss/datasources/PostgresDS"));

            } catch (NamingException ex) {
                LOG.error(ex.getMessage());
            }

        }
    }


    public AnsibleData insertAllAnsibleFacts(AnsibleData data) throws SQLException {

        getDataSource();

        String queryHostnameId = "SELECT id FROM server WHERE hostname = ? LIMIT 1";

        String insertAnsibleFacts = "INSERT INTO ansible_facts (facts, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ?))";

        String insertAnsibleServices = "INSERT INTO ansible_services (services, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ? ))";
        String insertAnsiblePackages = "INSERT INTO ansible_packages (packages, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ? ))";
        String insertAnsibleListeners = "INSERT INTO ansible_listeners (listeners, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ?))";
        String insertAnsibleRepolist = "INSERT INTO ansible_repo_list (repo_list, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ?))";
        String insertAnsibleProcesses = "INSERT INTO ansible_processes (processes, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ?))";
        String insertAnsibleUsersGroups = "INSERT INTO ansible_localusers_localgroups (local_users_groups, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ?))";
        String insertAnsibleCronJobs = "INSERT INTO ansible_cronjobs (cron_jobs, server_id) VALUES (to_json(?::json),(SELECT id FROM server WHERE hostname = ?))";

        Connection con = ds.getConnection();
        PreparedStatement stmtGetHostId = con.prepareStatement(queryHostnameId);

        PreparedStatement stmtFacts = con.prepareStatement(insertAnsibleFacts);
        PreparedStatement stmtServices = con.prepareStatement(insertAnsibleServices);
        PreparedStatement stmtPackages = con.prepareStatement(insertAnsiblePackages);
        PreparedStatement stmtListeners = con.prepareStatement(insertAnsibleListeners);
        PreparedStatement stmtRepoList = con.prepareStatement(insertAnsibleRepolist);
        PreparedStatement stmtProcesses = con.prepareStatement(insertAnsibleProcesses);
        PreparedStatement stmtUsersGroups = con.prepareStatement(insertAnsibleUsersGroups);
        PreparedStatement stmtCronJobs = con.prepareStatement(insertAnsibleCronJobs);

        con.setAutoCommit(false);

//        stmtGetHostId.setString(1, data.getHostname());
//        ResultSet rs = stmtGetHostId.executeQuery();
//
//        rs.next();
//        int hostId = rs.getInt("id");

        LOG.info("VALIDATING HOSTNAME FOR ANSIBLE FACTS INSERT: " + data.getHostname());

        LOG.info("Getting Facts for " + data.getHostname());
        stmtFacts.setString(1, data.getFacts());
        stmtFacts.setString(2, data.getHostname());

        LOG.info("Attempting data insert for Facts of " + data.getHostname());
        stmtFacts.execute();

        LOG.info("Getting service facts for " + data.getHostname());
        stmtServices.setString(1, data.getServices());
        stmtServices.setString(2, data.getHostname());

        LOG.info("Attempting data insert for service facts of " + data.getHostname());
        stmtServices.execute();

        LOG.info("Getting package facts for " + data.getHostname());
        stmtPackages.setString(1, data.getPackages());
        stmtPackages.setString(2, data.getHostname());

        LOG.info("Attempting data insert for package facts of " + data.getHostname());
        stmtPackages.execute();

        LOG.info("Getting netstat listener facts for " + data.getHostname());
        stmtListeners.setString(1, data.getListeners());
        stmtListeners.setString(2, data.getHostname());

        LOG.info("Attempting data insert for netstat listeners of " + data.getHostname());
        stmtListeners.execute();

        LOG.info("Getting yum repo facts for " + data.getHostname());
        stmtRepoList.setString(1, data.getRepo_list());
        stmtRepoList.setString(2, data.getHostname());

        LOG.info("Attempting data insert for yum repo facts of " + data.getHostname());
        stmtRepoList.execute();

        LOG.info("Getting Process facts for " + data.getHostname());
        stmtProcesses.setString(1, data.getProcesses());
        stmtProcesses.setString(2, data.getHostname());

        LOG.info("Attempting data insert for process facts of " + data.getHostname());
        stmtProcesses.execute();

        LOG.info("Getting local user and group facts for " + data.getHostname());
        stmtUsersGroups.setString(1, data.getUser_group());
        stmtUsersGroups.setString(2, data.getHostname());

        LOG.info("Attempting data insert for local user and group facts of " + data.getHostname());
        stmtUsersGroups.execute();

        LOG.info("Getting cron facts for " + data.getHostname());
        stmtCronJobs.setString(1,data.getCron_jobs());
        stmtCronJobs.setString(2, data.getHostname());

        LOG.info("Attempting data insert for cron facts of " + data.getHostname());
        stmtCronJobs.execute();

        LOG.info("Commiting data");
        con.commit();
        LOG.info("Closing db connection");
        con.close();

        return data;

    }

    public List<Map<String,Object>> getAppAnsibleFactsCSV(Integer eai_code){
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String,Object>> data = new ArrayList();


        String ansibleFactQuery = "SELECT parent_app.parent_app_name, parent_app.eai_code, server.hostname,"
                + " ansible_facts.facts AS facts,"
                + " ansible_services.services AS services,"
                + " ansible_packages.packages AS packages,"
                + " ansible_listeners.listeners AS listeners,"
                + " ansible_repo_list.repo_list AS repo_list,"
                + " ansible_cronjobs.cron_jobs AS cron_jobs,"
                + " ansible_localusers_localgroups.local_users_groups AS local_users_groups,"
                + " ansible_processes.processes AS processes"
                + " FROM parent_app,server,"
                + " ansible_facts,ansible_services,ansible_packages,ansible_cronjobs, "
                + " ansible_listeners, ansible_repo_list, ansible_localusers_localgroups, ansible_processes"
                + " WHERE parent_app.eai_code = ?"
                + " AND parent_app.id = server.parent_app_id "
                + " AND server.id = ansible_facts.server_id "
                + " AND server.id = ansible_services.server_id"
                + " AND server.id = ansible_packages.server_id "
                + " AND server.id = ansible_cronjobs.server_id "
                + " AND server.id = ansible_listeners.server_id "
                + " AND server.id = ansible_repo_list.server_id "
                + " AND server.id = ansible_localusers_localgroups.server_id"
                + " AND server.id = ansible_processes.server_id";


        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(ansibleFactQuery)){

            stmt.setInt(1, eai_code);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Map<String,Object> server = new HashMap();
                server.put("parent_app_name", rs.getString("eai_code"));
                server.put("eai_code", rs.getString("eai_code"));
                server.put("hostname", rs.getString("hostname"));

                try{
                    server.put("facts", mapper.readValue(rs.getString("facts"), Map.class));
                    server.put("packages", mapper.readValue(rs.getString("packages"), List.class));
                    server.put("services", mapper.readValue(rs.getString("services"), List.class));
                    server.put("listeners", mapper.readValue(rs.getString("listeners"), List.class));
                    server.put("repo_list", mapper.readValue(rs.getString("repo_list"), List.class));
                    server.put("cron_jobs", mapper.readValue(rs.getString("cron_jobs"), Map.class));
                    server.put("user_group", mapper.readValue(rs.getString("local_users_groups"), Map.class));
                    server.put("processes", mapper.readValue(rs.getString("processes"), List.class));

                }catch(Exception ex){
                    LOG.error(ex.getMessage());
                }
                data.add(server);

            }
            rs.close();
            con.close();

        }catch(SQLException ex){
            LOG.error(ex.getMessage());
        }

        return data;

    }

    public AnsibleData updateAnsibleFacts(AnsibleData data) {

        getDataSource();
        String updateFacts = "UPDATE ansible_facts SET facts = to_json(?::json) WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateFacts)) {
            stmt.setString(1, data.getFacts());
            stmt.setString(2, data.getHostname());

            stmt.executeUpdate();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());

        }
        return data;
    }

    public List<AnsibleFacts> retrieveAnsibleFacts(String hostname){
        ObjectMapper mapper = new ObjectMapper();
        AnsibleFacts data = new AnsibleFacts();
        List<AnsibleLVM> lvms = new ArrayList<>();
        List<AnsibleFacts> data_list = new ArrayList();

         String query = "SELECT id,server_id, facts ->> 'distribution_version' AS ansible_distribution_version,"
                 + " facts ->> 'processor_cores' AS ansible_processor_cores,"
                 + " facts ->> 'memtotal_mb' AS ansible_memtotal_mb,"
                 + " facts ->> 'architecture' AS ansible_architecture,"
                 + " facts ->> 'selinux' AS ansible_selinux,"
                 + " facts ->> 'kernel' AS ansible_kernel,"
                 + " facts -> 'lvm' ->> 'lvs' AS ansible_lvm,"
                 + " facts ->> 'mounts' AS ansible_mounts"
                 + " FROM ansible_facts WHERE server_id = (SELECT id FROM server WHERE hostname= ?) LIMIT 1";

          try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                data.getFacts().setAnsible_distribution_version(rs.getString("ansible_distribution_version"));
                data.getFacts().setAnsible_processor_cores(rs.getString("ansible_processor_cores"));
                data.getFacts().setAnsible_memtotal_mb(rs.getString("ansible_memtotal_mb"));
                data.getFacts().setAnsible_architecture(rs.getString("ansible_architecture"));
                data.getFacts().setAnsible_kernel(rs.getString("ansible_kernel"));
                data.getFacts().setAnsible_mounts(rs.getString("ansible_mounts"));
                try {
                    Map<String,Map<String, String>> old_lvm = mapper.readValue(rs.getString("ansible_lvm"), Map.class);


                    for(String key: old_lvm.keySet()){

                        Map<String, String> temp = old_lvm.get(key);
                        AnsibleLVM row = new AnsibleLVM();
                        row.setName((String)key);

                        row.setSize_g((String) temp.get("size_g"));
                        row.setVg((String) temp.get("vg"));
                        lvms.add(row);
                    }

                }catch(Exception ex){
                    LOG.error(ex.getMessage());
                }
            }

            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        data.getFacts().setAnsible_lvm(lvms);
        data_list.add(data);

        return data_list;

    }


    public AnsibleData updateAnsibleServices(AnsibleData data) {
        getDataSource();

        String updateAnsibleServices = "UPDATE ansible_services SET services = to_json(?::json) WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateAnsibleServices)) {

            stmt.setString(1, data.getServices());
            stmt.setString(2, data.getHostname());
            stmt.execute();

            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        return data;

    }

    public  List<AnsibleServices> retrieveAnsibleServices(String hostname) {
        AnsibleServices data = new AnsibleServices();
        List<AnsibleServices> data_list = new ArrayList<>();
        getDataSource();

        String retrieveSAnsibleServices = "SELECT * FROM ansible_services WHERE server_id = (SELECT id FROM server WHERE hostname = ?) LIMIT 1";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(retrieveSAnsibleServices)) {

            stmt.setString(1, hostname);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                data.setServices(rs.getString("services"));
            }

            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        data_list.add(data);

        return data_list;
    }

    public AnsibleData updateAnsiblePackages(AnsibleData data) {
        getDataSource();

        String updateAnsiblePackages = "UPDATE ansible_packages SET packages = to_json(?::json) WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateAnsiblePackages)) {

            stmt.setString(1, data.getPackages());
            stmt.setString(2, data.getHostname());
            stmt.execute();

            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        return data;
    }

    public List<AnsiblePackages> retrieveAnsiblePackages(String hostname) {
        AnsiblePackages data = new AnsiblePackages();
        List<AnsiblePackages> data_list = new ArrayList();

        getDataSource();

        String query = "SELECT * FROM ansible_packages WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                String packages = rs.getString("packages");
                data.setPackages(packages);
            }

            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        data_list.add(data);
        return data_list;
    }

    public AnsibleData updateAnsibleListeners(AnsibleData data) {
        getDataSource();

        String updateAnsiblePackages = "UPDATE ansible_listeners SET listeners = to_json(?::json) WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateAnsiblePackages)) {

            stmt.setString(1, data.getListeners());
            stmt.setString(2, data.getHostname());
            stmt.execute();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        return data;
    }

    public List<AnsibleListener> retrieveAnsibleListeners(String hostname) {

        AnsibleListener data = new AnsibleListener();
        List<AnsibleListener> data_list = new ArrayList<>();
        getDataSource();

        String query = "SELECT * FROM ansible_listeners WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                data.setListeners(rs.getString("listeners"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        data_list.add(data);

        return data_list;
    }

    public AnsibleData updateAnsibleCronJobs(AnsibleData data) {
        getDataSource();

        String updateCronjobs = "UPDATE ansible_cronjobs SET cron_jobs = ? WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateCronjobs)) {

            stmt.setString(1, data.getCron_jobs());
            stmt.setString(2, data.getHostname());
            stmt.execute();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        return data;

    }

    public AnsibleData retrieveAnsibleCronJobs(String hostname) {
        AnsibleData data = new AnsibleData();
        getDataSource();

        String query = "SELECT * FROM ansible_cronjobs WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setHostname(hostname);
                data.setCron_jobs(rs.getString("cron_jobs"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        return data;

    }

    public AnsibleData updateAnsibleRepoList(AnsibleData data) {
        getDataSource();

        String updateRepoList = "UPDATE ansible_repo_list SET repo_list = ? WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateRepoList)) {

            stmt.setString(1, data.getRepo_list());
            stmt.setString(2, data.getHostname());
            stmt.execute();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        return data;

    }

    public AnsibleData retrieveAnsibleRepoList(String hostname) {
        AnsibleData data = new AnsibleData();
        getDataSource();

        String query = "SELECT * FROM ansible_repo_list WHERE server_id = (SELECT id FROM server WHERE hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setHostname(hostname);
                data.setRepo_list(rs.getString("repo_list"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        return data;
    }

    public AnsibleData updateAnsibleUsersGroups(AnsibleData data) {
        getDataSource();

        String updateRepoList = "UPDATE ansible_localusers_localgroups SET local_users_groups = ? WHERE server_id = (select id from server where hostname = ?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateRepoList)) {

            stmt.setString(1, data.getRepo_list());
            stmt.setString(2, data.getHostname());
            stmt.execute();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        return data;
    }

    public List<AnsibleUsers> retrieveAnsibleUsers(String hostname){
        AnsibleUsers data = new AnsibleUsers();
        List<AnsibleUsers> data_list = new ArrayList<>();
        getDataSource();
        String query = "select id,server_id,local_users_groups ->> 'users' AS users from ansible_localusers_localgroups WHERE server_id = (select id from server where hostname=?)";

        try (Connection con = ds.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                data.setUsers(rs.getString("users"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        data_list.add(data);

        return data_list;
    }

    public List<AnsibleGroups> retrieveAnsibleGroups(String hostname){
        AnsibleGroups data = new AnsibleGroups();
        List<AnsibleGroups> data_list = new ArrayList<>();
        getDataSource();
        String query = "select id,server_id,local_users_groups ->> 'groups' AS groups from ansible_localusers_localgroups WHERE server_id = (select id from server where hostname=?)";

        try (Connection con = ds.getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                data.setGroups(rs.getString("groups"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        data_list.add(data);

        return data_list;

    }

//    public List<AnsibleUsersGroups> retrieveAnsiblSUsersGroups(String hostname) {
//
//        AnsibleUsersGroups data = new AnsibleUsersGroups();
//        List<AnsibleUsersGroups> data_list = new ArrayList<>();
//
//        getDataSource();
//
//        String query = "SELECT users_groups( FROM ansible_localusers_localgroups WHERE server_id = (select id from server where hostname = ?)";
//        try (Connection con = ds.getConnection();
//                PreparedStatement stmt = con.prepareStatement(query)) {
//
//            stmt.setString(1, hostname);
//            ResultSet rs = stmt.executeQuery();
//            while (rs.next()) {
//                data.setHostname(hostname);
//                data.setId(rs.getInt("id"));
//                data.setServer_id(rs.getInt("server_id"));
//                data.setUser_group(rs.getString("users_groups"));
//            }
//            rs.close();
//            con.close();
//        } catch (SQLException ex) {
//            LOG.error(ex.getMessage());
//        }
//
//        return data_list;
//    }

    public AnsibleData updateAnsibleProcesses(AnsibleData data){
        getDataSource();

        String updateRepoList = "UPDATE ansible_processes SET processes = ? WHERE server_id = (select id from server where hostname = ?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(updateRepoList)) {

            stmt.setString(1, data.getProcesses());
            stmt.setString(2, data.getHostname());
            stmt.execute();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        return data;
    }

    public List<AnsibleProcesses>  retrieveAnsibleProcesses(String hostname){
        AnsibleProcesses data = new AnsibleProcesses();
        List<AnsibleProcesses> data_list = new ArrayList<>();
        getDataSource();

        String query = "SELECT * FROM ansible_processes WHERE server_id = (select id from server where hostname = ?)";
        try (Connection con = ds.getConnection();
                PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, hostname);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                data.setHostname(hostname);
                data.setId(rs.getInt("id"));
                data.setServer_id(rs.getInt("server_id"));
                data.setProcesses(rs.getString("processes"));
            }
            rs.close();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
        data_list.add(data);
        return data_list;

    }

    public List<AppCode> retrieveAppCodes(){
        List<AppCode> data = new ArrayList<>();
        getDataSource();
        String query = "SELECT * FROM parent_app";

        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                LOG.info(rs.getString("parent_app_name"));

                AppCode app = new AppCode();
                app.setId(rs.getInt("id"));
                app.setAppcode(rs.getInt("eai_code"));
                app.setApp(rs.getString("parent_app_name"));
                data.add(app);
            }
            rs.close();
            con.close();

        }catch( SQLException ex){
            LOG.error(ex.getMessage());
        }

        return data;

    }

    public List<AppData> retrieveAppData(int appcode){

        List<AppData> data = new ArrayList<>();
        getDataSource();

        String query = "SELECT * from server JOIN parent_app on server.parent_app_id=parent_app.id "
                + "AND parent_app.eai_code=?";

        try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)){
            stmt.setInt(1, appcode);
            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
                AppData row = new AppData();
                row.setId(rs.getInt("id"));
                row.setAppcode(rs.getInt("eai_code"));
                row.setParent_app_name(rs.getString("parent_app_name"));
                row.setHostname(rs.getString("hostname"));
                row.setFqdn(rs.getString("fqdn"));
                row.setSat_ipv4_addr(rs.getString("sat_ipv4_addr"));
                row.setEnvironments(rs.getString("environments"));
                row.setMigration_status(rs.getString("migration_status"));
                row.setDestination_host(rs.getString("dest_hostname"));

                data.add(row);

            }
            rs.close();
            con.close();

        }catch( SQLException ex){
            LOG.error(ex.getMessage());
        }

        return data;

    }

    public List<Hostname> retrieveServers(){
        List<Hostname> data = new ArrayList<>();
        getDataSource();

        String query = "Select id,hostname from server";

         try(Connection con = ds.getConnection();
            PreparedStatement stmt = con.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();


            while(rs.next()){
                Hostname row = new Hostname();
                row.setId(rs.getInt("id"));
                row.setHostname(rs.getString("hostname"));
                data.add(row);
            }
            rs.close();
            con.close();

        }catch( SQLException ex){
            LOG.error(ex.getMessage());
        }

        return data;

    }

    //CSV QUERIES
    public void addCsvServerData(CsvServerImport data) {

        getDataSource();

        String insertParentApp = "INSERT INTO parent_app (parent_app_name, eai_code, in_scope) SELECT ?, ?,? "
                + "WHERE NOT EXISTS (SELECT 1 FROM parent_app WHERE eai_code=?)";

        String queryParentAppId = "SELECT id FROM parent_app WHERE eai_code = ? LIMIT 1" ;

        String insertServer = "INSERT INTO server (hostname, fqdn, sat_ipv4_addr, sat_name, dns_ipv4_addr,rhel_version, services, environments, parent_app_id) "
                + "VALUES (?,?,?,?,?,?,?,?,(SELECT id FROM parent_app WHERE eai_code = ? LIMIT 1))";

        String queryServerId = "SELECT id FROM server WHERE hostname = ? LIMIT 1";

        String insertReplace = "INSERT INTO replace_status (replaced, ipv4_old,server_id) SELECT false,?,? WHERE NOT EXISTS (SELECT 1 FROM replace_status WHERE server_id =?)";

        String insertOwner = "INSERT INTO ownership(financial_lob, project_manager, business_owner, parent_app_id) SELECT ?,?,?,? WHERE NOT EXISTS (SELECT 1 FROM ownership WHERE parent_app_id=?)";

        try (Connection con = ds.getConnection();
                PreparedStatement stmtParent = con.prepareStatement(insertParentApp);
                PreparedStatement stmtServer = con.prepareStatement(insertServer);
                PreparedStatement stmtQueryServerId = con.prepareStatement(queryServerId);
                PreparedStatement stmtOwnership2 = con.prepareStatement(insertOwner);
                PreparedStatement stmtReplace = con.prepareStatement(insertReplace);
                PreparedStatement stmtQueryId = con.prepareStatement(queryParentAppId)) {

            con.setAutoCommit(false);

            //insert parent app
            stmtParent.setString(1, data.getParentAppCsv().getParent_app_name());
            stmtParent.setInt(2, data.getParentAppCsv().getEai_code());
            stmtParent.setBoolean(3, data.getParentAppCsv().getScope());
            stmtParent.setInt(4, data.getParentAppCsv().getEai_code());

            stmtParent.execute();

            stmtQueryId.setInt(1, data.getParentAppCsv().getEai_code());
            ResultSet rs_parentId = stmtQueryId.executeQuery();
            rs_parentId.next();
            Integer parent_id = rs_parentId.getInt("id");

            //insert server
            stmtServer.setString(1, data.getServerCsv().getHostname());
            stmtServer.setString(2, data.getServerCsv().getFqdn());
            stmtServer.setString(3, data.getServerCsv().getSat_ipv4_addr());
            stmtServer.setString(4, data.getServerCsv().getSat_name());
            stmtServer.setString(5, data.getServerCsv().getDns_ip());
            stmtServer.setInt(6, data.getServerCsv().getRhel_version());
            stmtServer.setString(7,data.getServerCsv().getServices());
            stmtServer.setString(8, data.getServerCsv().getEnvironment());
            stmtServer.setInt(9, data.getParentAppCsv().getEai_code());
            stmtServer.execute();

            stmtQueryServerId.setString(1, data.getServerCsv().getHostname());
            ResultSet rs_serverId = stmtQueryServerId.executeQuery();
            rs_serverId.next();
            Integer server_id = rs_serverId.getInt("id");

            //insert ownership
            stmtOwnership2.setString(1, data.getOwnershipCsv().getFinancial_lob());
            stmtOwnership2.setString(2, data.getOwnershipCsv().getProject_manager());
            stmtOwnership2.setString(3, data.getOwnershipCsv().getBusiness_owner());
            stmtOwnership2.setInt(4, parent_id);
            stmtOwnership2.setInt(5, parent_id);

            stmtOwnership2.execute();

            //insert replace_status
            stmtReplace.setString(1, data.getServerCsv().getSat_ipv4_addr());
            stmtReplace.setInt(2, server_id);
            stmtReplace.setInt(3, server_id);

            stmtReplace.execute();

            con.commit();
            con.close();

        } catch (SQLException ex) {

            LOG.error(ex.getMessage());

        }

    }

    public void updateCSVServerData(CsvServerImport data) {

        getDataSource();

        //update parent app data
        String updateParentApp = "UPDATE parent_app SET in_scope = ? WHERE eai_code = ?";
        //update server
        String updateServer = "UPDATE server SET environments = ?, services = ? WHERE hostname = ?";


        try (Connection con = ds.getConnection();
                PreparedStatement stmtUpdateParent = con.prepareStatement(updateParentApp);
                PreparedStatement stmtUpdateServer = con.prepareStatement(updateServer)) {

            stmtUpdateParent.setBoolean(1,data.getParentAppCsv().getScope());
            stmtUpdateParent.setInt(2, data.getParentAppCsv().getEai_code());

            stmtUpdateParent.executeUpdate();

            stmtUpdateServer.setString(1, data.getServerCsv().getEnvironment());
            stmtUpdateServer.setString(2, data.getServerCsv().getServices());
            stmtUpdateServer.setString(3, data.getServerCsv().getHostname());

            stmtUpdateServer.executeUpdate();

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
    }

    public void insertCsvQuestionnaireData(List<CsvQuestionnaireImport> data) {

        String insertGeneral = this.questionnaireInsertString("general_information");
        String insertApplication = this.questionnaireInsertString("application_information");
        String insertSpecific = this.questionnaireInsertString("specific_information");
        String insertOwnership = this.questionnaireInsertString("ownership_and_leads");
        String insertImpact = this.questionnaireInsertString("impact");
        String insertInterfaces = this.questionnaireInsertString("interfaces_dependencies");
        String insertEnvironmnent = this.questionnaireInsertString("environment_information");
        String insertTechnical = this.questionnaireInsertString("technical");
        String insertApplicationConfig = this.questionnaireInsertString("application_configuration");
        String insertNetwork = this.questionnaireInsertString("network_configuration");
        String insertHardware = this.questionnaireInsertString("hardware_configuration");
        String insertPerformance = this.questionnaireInsertString("performance");
        String insertDocs = this.questionnaireInsertString("documentation_infrastructure");
        String insertNotes = this.questionnaireInsertString("notes");

        try (Connection con = ds.getConnection()) {

            con.setAutoCommit(false);

            Map<String, PreparedStatement> statements = new HashMap();

            statements.put("1", con.prepareStatement(insertGeneral));
            statements.put("2", con.prepareStatement(insertApplication));
            statements.put("3", con.prepareStatement(insertSpecific));
            statements.put("4", con.prepareStatement(insertOwnership));
            statements.put("5", con.prepareStatement(insertImpact));
            statements.put("6", con.prepareStatement(insertInterfaces));
            statements.put("7", con.prepareStatement(insertEnvironmnent));
            statements.put("8", con.prepareStatement(insertTechnical));
            statements.put("9", con.prepareStatement(insertApplicationConfig));
            statements.put("10", con.prepareStatement(insertHardware));
            statements.put("11", con.prepareStatement(insertNetwork));
            statements.put("12", con.prepareStatement(insertPerformance));
            statements.put("13", con.prepareStatement(insertDocs));
            statements.put("14", con.prepareStatement(insertNotes));

            for (CsvQuestionnaireImport row : data) {

                PreparedStatement stmt = (PreparedStatement) statements.get(row.getCategoryNumber());
                stmt = this.setPreparedInsertData(stmt, row);
                stmt.execute();
            }

            con.commit();
            con.close();

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

    }

    public void updateCsvQuestionnaireData(List<CsvQuestionnaireImport> data) {

        String insertGeneral = this.questionnaireUpdateString("general_information");
        String insertApplication = this.questionnaireUpdateString("application_information");
        String insertSpecific = this.questionnaireUpdateString("specific_information");
        String insertOwnership = this.questionnaireUpdateString("ownership_and_leads");
        String insertImpact = this.questionnaireUpdateString("impact");
        String insertInterfaces = this.questionnaireUpdateString("interfaces_dependencies");
        String insertEnvironmnent = this.questionnaireUpdateString("environment_information");
        String insertTechnical = this.questionnaireUpdateString("technical");
        String insertApplicationConfig = this.questionnaireUpdateString("application_configuration");
        String insertNetwork = this.questionnaireUpdateString("network_configuration");
        String insertHardware = this.questionnaireUpdateString("hardware_configuration");
        String insertPerformance = this.questionnaireUpdateString("performance");
        String insertDocs = this.questionnaireUpdateString("documentation_infrastructure");
        String insertNotes = this.questionnaireUpdateString("notes");

        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);

            Map<String, PreparedStatement> statements = new HashMap();

            statements.put("1", con.prepareStatement(insertGeneral));
            statements.put("2", con.prepareStatement(insertApplication));
            statements.put("3", con.prepareStatement(insertSpecific));
            statements.put("4", con.prepareStatement(insertOwnership));
            statements.put("5", con.prepareStatement(insertImpact));
            statements.put("6", con.prepareStatement(insertInterfaces));
            statements.put("7", con.prepareStatement(insertEnvironmnent));
            statements.put("8", con.prepareStatement(insertTechnical));
            statements.put("9", con.prepareStatement(insertApplicationConfig));
            statements.put("10", con.prepareStatement(insertHardware));
            statements.put("11", con.prepareStatement(insertNetwork));
            statements.put("12", con.prepareStatement(insertPerformance));
            statements.put("13", con.prepareStatement(insertDocs));
            statements.put("14", con.prepareStatement(insertNotes));

            for (CsvQuestionnaireImport row : data) {

                PreparedStatement stmt = (PreparedStatement) statements.get(row.getCategoryNumber());
                stmt = this.setPreparedUpdateData(stmt, row);
                stmt.execute();
            }

            con.commit();
            con.close();
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());

        }
    }

    private String questionnaireInsertString(String table) {
        String insert = "INSERT INTO " + table + " (Category_Number,"
                + "Category,"
                + "Ref,"
                + "Answering_Team,"
                + "Attempt_to_Prefill,"
                + "Question,"
                + "Answer,"
                + "Notes, "
                + "parent_app_id) "
                + "VALUES (?,?,?,?,?,?,?,?,(SELECT id FROM parent_app WHERE eai_code= ?))";

        return insert;

    }

    private String questionnaireUpdateString(String table) {

        String update = "UPDATE " + table + " SET Answer = ?, Notes = ? WHERE Ref = ? AND parent_app_id = (SELECT id FROM parent_app WHERE eai_code = ?)";
        return update;
    }


    private PreparedStatement setPreparedInsertData(PreparedStatement stmt, CsvQuestionnaireImport data) {

        try {
            stmt.setInt(1, Integer.parseInt(data.getCategoryNumber()));
            stmt.setString(2, data.getCategory());
            stmt.setInt(3, Integer.parseInt(data.getReference()));
            stmt.setString(4, data.getAnswering_team());
            stmt.setString(5, data.getPrefill());
            stmt.setString(6, data.getQuestion());
            stmt.setString(7, data.getAnswer());
            stmt.setString(8, data.getNotes());
            stmt.setInt(9, data.getEai_code());

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        return stmt;
    }

    private PreparedStatement setPreparedUpdateData(PreparedStatement stmt, CsvQuestionnaireImport data) {

        try {
            stmt.setString(1, data.getAnswer());
            stmt.setString(2, data.getNotes());
            stmt.setInt(3, Integer.parseInt(data.getReference()));
            stmt.setInt(4, data.getEai_code());

        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }

        return stmt;
    }

}
