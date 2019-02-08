package com.newlight77.kata.excel.datamodel;

import java.util.List;

public class Sondage {

    private String id;
    private String sommaire;
    private String client;
    private Adresse adresseClient;
    private List<Question> questions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSommaire() {
        return sommaire;
    }

    public void setSommaire(String sommaire) {
        this.sommaire = sommaire;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Adresse getAdresseClient() {
        return adresseClient;
    }

    public void setAdresseClient(Adresse adresseClient) {
        this.adresseClient = adresseClient;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

