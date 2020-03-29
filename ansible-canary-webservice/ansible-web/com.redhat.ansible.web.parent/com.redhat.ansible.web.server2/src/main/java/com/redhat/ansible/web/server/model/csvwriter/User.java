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
public class User {

    @DataField(pos = 2)
    private String username;
    @DataField(pos = 3)
    private String home_directory;
    @DataField(pos = 4)
    private String login_shell;
    @DataField(pos = 5)
    private String uid;
    @DataField(pos = 6)
    private String encrypted_password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHome_directory() {
        return home_directory;
    }

    public void setHome_directory(String home_directory) {
        this.home_directory = home_directory;
    }

    public String getLogin_shell() {
        return login_shell;
    }

    public void setLogin_shell(String login_shell) {
        this.login_shell = login_shell;
    }

    public String getEncrypted_password() {
        return encrypted_password;
    }

    public void setEncrypted_password(String encrypted_password) {
        this.encrypted_password = encrypted_password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
