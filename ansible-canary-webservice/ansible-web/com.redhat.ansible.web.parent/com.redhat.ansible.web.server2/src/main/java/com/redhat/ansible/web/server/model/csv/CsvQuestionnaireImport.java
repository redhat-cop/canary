/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.redhat.ansible.web.server.model.csv;

import javax.inject.Named;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

/**
 *
 * @author dfreese
 */
@CsvRecord(separator = ",")
@Named("CsvQuestionnaireImport")
public class CsvQuestionnaireImport {

    @DataField(pos = 1)
    private Integer eai_code;
    @DataField(pos = 2)
    private String categoryNumber;
    @DataField(pos = 3)
    private String category;
    @DataField(pos = 4)
    private String reference;
    @DataField(pos = 5)
    private String answering_team;
    @DataField(pos = 6)
    private String prefill;
    @DataField(pos = 7)
    private String question;
    @DataField(pos = 8)
    private String answer;
    @DataField(pos = 9)
    private String notes;

    public Integer getEai_code() {
        return eai_code;
    }

    public void setEai_code(Integer eai_code) {
        this.eai_code = eai_code;
    }

    public String getCategoryNumber() {
        return categoryNumber;
    }

    public void setCategoryNumber(String categoryNumber) {
        this.categoryNumber = categoryNumber;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getAnswering_team() {
        return answering_team;
    }

    public void setAnswering_team(String answering_team) {
        this.answering_team = answering_team;
    }

    public String getPrefill() {
        return prefill;
    }

    public void setPrefill(String prefill) {
        this.prefill = prefill;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
