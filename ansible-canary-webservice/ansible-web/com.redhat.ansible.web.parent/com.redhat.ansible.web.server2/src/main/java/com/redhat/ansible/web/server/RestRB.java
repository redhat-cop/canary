/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.redhat.ansible.web.server.model.AnsibleData;
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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfreese
 */
@ContextName("ansible-context")
public class RestRB extends RouteBuilder {

    public static final Logger LOG = LoggerFactory.getLogger(RestRB.class);

    public static final String CONTEXT_PATH = "/ansible/web";

    private static final String CSV_OUTPUT_FILE_PATH = "file:////home/canary/csvdata/out/?autoCreate=true&fileExist=Append";

    @Override
    public void configure() throws Exception {

        DataFormat generalBindy = new BindyCsvDataFormat(CsvGeneral.class);
        DataFormat lvmBindy = new BindyCsvDataFormat(CsvLVM.class);
        DataFormat mountbindy = new BindyCsvDataFormat(CsvMounts.class);
        DataFormat nicBindy = new BindyCsvDataFormat(CsvNetworkInterfaces.class);
        DataFormat listenerBindy = new BindyCsvDataFormat(CsvListeners.class);
        DataFormat repolistBindy = new BindyCsvDataFormat(CsvRepoLists.class);
        DataFormat processBindy = new BindyCsvDataFormat(CsvProcesses.class);
        DataFormat usersBindy = new BindyCsvDataFormat(CsvUsers.class);
        DataFormat groupBindy = new BindyCsvDataFormat(CsvGroups.class);
        DataFormat cronBindy = new BindyCsvDataFormat(CsvCronJob.class);
        DataFormat packageBindy = new BindyCsvDataFormat(CsvPackages.class);
        DataFormat serviceBindy = new BindyCsvDataFormat(CsvServiceScripts.class);

        restConfiguration().component("servlet").host("0.0.0.0").port(8080).bindingMode(RestBindingMode.json).skipBindingOnErrorCode(false).enableCORS(true);

        onException(JsonProcessingException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody().constant("Json Body Processing Error");

        onException(JsonParseException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody().constant("Json body is invalid");

        onException(SQLException.class)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .setBody().constant("Database error for SQL statement");

        //ENDPOINTS FOR ANSIBLE TOWER
        //post all facts collected
        rest(CONTEXT_PATH).post("/servers/{hostname}/insertAnsibleData").type(Map.class).outType(AnsibleData.class).route().routeId(RestRB.class.getCanonicalName() + "post-ansibledata").to("direct:split_path");
        from("direct:split_path").multicast().stopOnException().to("direct:dbService","direct:csvService");

        from("direct:csvService").multicast().to("direct:generalCsv", "direct:lvmCsv", "direct:mountCsv",
                "direct:nicCsv", "direct:listenersCsv", "direct:repoListCsv",
                "direct:processesCsv","direct:cronCsv","direct:packagesCsv","direct:servicesCsv", "direct:usersGroups");

        from("direct:generalCsv")
                .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-general.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=generalFactsCsv").marshal(generalBindy).to(CSV_OUTPUT_FILE_PATH);
        from("direct:lvmCsv")
                .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-lvm.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=lvmFactsCsv").marshal(lvmBindy).to(CSV_OUTPUT_FILE_PATH);
        from("direct:mountCsv")
                .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-mount.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=mountFactsCsv").marshal(mountbindy).to(CSV_OUTPUT_FILE_PATH);
        from("direct:nicCsv")
                .process(ex -> {
                Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-nic.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=nicFactsCsv").marshal(nicBindy).to(CSV_OUTPUT_FILE_PATH);
        from("direct:listenersCsv")
                .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-listeners.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=listenerFactsCsv").marshal(listenerBindy).to(CSV_OUTPUT_FILE_PATH);

        from("direct:repoListCsv")
                .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-repolist.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=repolistFactsCsv").marshal(repolistBindy).to(CSV_OUTPUT_FILE_PATH);

        from("direct:processesCsv")
            .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-processes.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
                })
                .to("bean:CsvService?method=processesFactsCsv").marshal(processBindy).to(CSV_OUTPUT_FILE_PATH);
        from("direct:cronCsv")
            .process(ex -> {
                Map<String,Object> data = ex.getIn().getBody(Map.class);
                String file_header = data.get("parent_app_name") + "-cronJobs.csv";
                ex.getOut().setBody(data);
                ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
            })
            .to("bean:CsvService?method=cronJobFactsCsv").marshal(cronBindy).to(CSV_OUTPUT_FILE_PATH);

        from("direct:packagesCsv")
            .process(ex -> {
                Map<String,Object> data = ex.getIn().getBody(Map.class);
                String file_header = data.get("parent_app_name") + "-packages.csv";
                ex.getOut().setBody(data);
                ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
            })
            .to("bean:CsvService?method=packageFactsCsv").marshal(packageBindy).to(CSV_OUTPUT_FILE_PATH);

        from("direct:servicesCsv")
            .process(ex -> {
                Map<String,Object> data = ex.getIn().getBody(Map.class);
                String file_header = data.get("parent_app_name") + "-services.csv";
                ex.getOut().setBody(data);
                ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
            })
            .to("bean:CsvService?method=serviceFactsCsv").marshal(serviceBindy).to(CSV_OUTPUT_FILE_PATH);

        from("direct:usersGroups").multicast().to("direct:usersCsv", "direct:groupsCsv");

        from("direct:usersCsv")
            .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-localusers.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
            })
            .to("bean:CsvService?method=userFactsCsv").marshal(usersBindy).to(CSV_OUTPUT_FILE_PATH);

        from("direct:groupsCsv")
            .process(ex -> {
                    Map<String,Object> data = ex.getIn().getBody(Map.class);
                    String file_header = data.get("parent_app_name") + "-localgroups.csv";
                    ex.getOut().setBody(data);
                    ex.getOut().setHeader(Exchange.FILE_NAME, file_header);
            })
            .to("bean:CsvService?method=groupFactsCsv").marshal(groupBindy).to(CSV_OUTPUT_FILE_PATH)
                .process(ex -> {
                    ex.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
                    ex.getOut().setBody("successful");
                });

        from("direct:dbService").to("bean:DiscoveryService?method=insertAnsibleData").endRest();

        //create new CSV files rest endpoint
        rest(CONTEXT_PATH).get("/parent_app/print_facts_csv/{eai_code}").outType(Map.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-print_facts_csv")
                .to("bean:DiscoveryService?method=getAppAnisbleFactsCSV")
                .process(ex -> {

                    List<Map<String,Object>> datapoints = ex.getIn().getBody(List.class);
                    Map<String,Object> data = datapoints.get(0);
                    String parent_app_name = (String) data.get("parent_app_name");

                   Files.walk(Paths.get("/home/canary/csvdata/out/"))
                           .map(String::valueOf)
                           .filter(path -> path.contains(parent_app_name))
                           .forEach( p -> {
                                File file = new File(p);
                                file.delete();
                           });
                })
                .split(body())
                .to("direct:csvService");

        //ENDPOINTS FOR UI

        //ansible facts
        rest(CONTEXT_PATH).put("/ansible_facts/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_facts")
                .to("bean:DiscoveryService?method=updateAnsibleFacts");

        //ansible_facts setup to retrieve a subset of the data
        rest(CONTEXT_PATH).get("/ansible_facts/{hostname}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_facts")
                .to("bean:DiscoveryService?method=retrieveAnsibleFacts");

        //ansible services done
        rest(CONTEXT_PATH).put("/ansible_services/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_services")
                .to("bean:DiscoveryService?method=updateAnsibleServices");

        rest(CONTEXT_PATH).get("/ansible_services/{hostname}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_services")
                .to("bean:DiscoveryService?method=retrieveAnsibleServices");

        //ansible packages
        rest(CONTEXT_PATH).put("/ansible_packages/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_packages")
                .to("bean:DiscoveryService?method=updateAnsiblePackages");

        rest(CONTEXT_PATH).get("/ansible_packages/{hostname}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_packages")
                .to("bean:DiscoveryService?method=retrieveAnsiblePackages");

        //ansible_listeners done
        rest(CONTEXT_PATH).put("/ansible_listeners/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_listeners")
                .to("bean:DiscoveryService?method=updateAnsibleListeners");

        rest(CONTEXT_PATH).get("/ansible_listeners/{hostname}").type(Map.class).outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_listeners")
                .to("bean:DiscoveryService?method=retrieveAnsibleListeners");

        //ansible cron jobs
        rest(CONTEXT_PATH).put("/ansible_cronjobs/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_cronJobs")
                .to("bean:DiscoveryService?method=updateAnsibleCronjobs");

        rest(CONTEXT_PATH).get("/ansible_cronjobs/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_cronJobs")
                .to("bean:DiscoveryService?method=retrieveAnsibleCronjobs");

        //ansible_repo_list
        rest(CONTEXT_PATH).put("/ansible_repolist/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_repolist")
                .to("bean:DiscoveryService?method=updateAnsibleRepoList");

        rest(CONTEXT_PATH).get("/ansible_repolist/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_repolist")
                .to("bean:DiscoveryService?method=retrieveAnsibleRepoList");


        //ansible_retrieve users
        rest(CONTEXT_PATH).get("/ansible_users/{hostname}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_localusers")
                .to("bean:DiscoveryService?method=retrieveAnsibleUsers");

         //ansible_retrieve groups
        rest(CONTEXT_PATH).get("/ansible_groups/{hostname}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_localgroups")
                .to("bean:DiscoveryService?method=retrieveAnsibleGroups");


        //ansible_processes done
        rest(CONTEXT_PATH).put("/ansible_processes/{hostname}").type(Map.class).outType(AnsibleData.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-put-ansible_processes")
                .to("bean:DiscoveryService?method=updateAnsibleProcesses");

        rest(CONTEXT_PATH).get("/ansible_processes/{hostname}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-ansible_processes")
                .to("bean:DiscoveryService?method=retrieveAnsibleProcesses");

        //get list of services done
        rest(CONTEXT_PATH).get("/appcodes/").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-appcodes")
                .to("bean:DiscoveryService?method=retrieveAppCodes");

        rest(CONTEXT_PATH).get("/appData/{appcode}").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-appData")
                .to("bean:DiscoveryService?method=retrieveAppData");

        //get list of hostnames
        rest(CONTEXT_PATH).get("/servers/").outType(List.class)
                .route().routeId(RestRB.class.getCanonicalName() + "-get-servers")
                .to("bean:DiscoveryService?method=retrieveServers");


    }

}
