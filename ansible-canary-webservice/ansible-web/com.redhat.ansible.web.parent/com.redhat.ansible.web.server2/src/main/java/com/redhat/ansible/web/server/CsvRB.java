/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server;

import com.redhat.ansible.web.server.model.csv.CsvQuestionnaireImport;
import com.redhat.ansible.web.server.model.csv.CsvServerImport;
import java.sql.SQLException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.cdi.ContextName;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.apache.camel.spi.DataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author dfreese
 */
@ContextName("ansible-context")
public class CsvRB extends RouteBuilder {

    public static final Logger LOG = LoggerFactory.getLogger(CsvRB.class);

    private final String INSERT_SERVER_CSV = "file:////home/canary/csvdata/in/server/post/";
    private final String UPDATE_SERVER_CSV = "file:////home/canary/csvdata/in/server/update/";
    private final String INSERT_QUESTIONNAIRE_CSV = "file:////home/canary/csvdata/in/questionnaire/post/";
    private final String UPDATE_QUESTIONNAIRE_CSV = "file:////home/canary/csvdata/in/questionnaire/update/";

    @Override
    public void configure() throws Exception {

        onException(SQLException.class)
            .handled(true);

        DataFormat serverBindy = new BindyCsvDataFormat(CsvServerImport.class);
        DataFormat questionBindy = new BindyCsvDataFormat(CsvQuestionnaireImport.class);

        //SERVER ENDPOINTS
        from(INSERT_SERVER_CSV)
                .log("inserting server data from csv")
                .unmarshal(serverBindy)
                .split(body())
                .to("bean:DiscoveryService?method=csvServerInsert");

        from(UPDATE_SERVER_CSV)
                .log("updating server data from csv")
                .unmarshal(serverBindy)
                .split(body())
                .to("bean:DiscoveryService?method=csvServerUpdate");

        //QUESTIONNAIRE ENDPOINTS
        from(INSERT_QUESTIONNAIRE_CSV)
                .log("inserting questionnaire data from csv")
                .unmarshal(questionBindy)
                .to("bean:DiscoveryService?method=csvQuestionnaireInsert");

        from(UPDATE_QUESTIONNAIRE_CSV)
                .log("updating questionnaire data from csv")
                .unmarshal(questionBindy)
                .to("bean:DiscoveryService?method=csvQuestionnaireUpdate");

    }

}
