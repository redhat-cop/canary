/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csvwriter;

import org.apache.camel.dataformat.bindy.annotation.DataField;
import org.apache.camel.dataformat.bindy.annotation.Link;

/**
 *
 * @author dfreese
 */
@Link
public class RepoList {

    @DataField(pos = 2)
    private String repoId;
    @DataField(pos = 3)
    private String repoName;
    @DataField(pos = 4)
    private String repoStatus;

    public String getRepoId() {
        return repoId;
    }

    public void setRepoId(String repoId) {
        this.repoId = repoId;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

    public String getRepoStatus() {
        return repoStatus;
    }

    public void setRepoStatus(String repoStatus) {
        this.repoStatus = repoStatus;
    }

}
