package com.newlight77.kata.excel.datamodel;

public class AdresseEffectue {
    private String id;
    private Adresse adresse;
    private Statut statut;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
