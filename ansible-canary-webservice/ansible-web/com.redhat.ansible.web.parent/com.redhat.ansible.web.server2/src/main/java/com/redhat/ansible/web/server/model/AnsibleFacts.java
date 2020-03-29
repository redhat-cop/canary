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
public class AnsibleFacts extends CoreData {

    private Facts facts; 

    public Facts getFacts() {
        if(facts == null){
            facts = new Facts(); 
        }
        return facts;
    }

    public void setFacts(Facts facts) {
        this.facts = facts;
    }
    
}
