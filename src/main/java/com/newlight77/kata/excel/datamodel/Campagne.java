package com.newlight77.kata.excel.datamodel;

import java.util.List;

public class Campagne {

    private String id ;
    private String sondageId;
    private List<AdresseEffectue> adresseEffectues;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSondageId() {
        return sondageId;
    }

    public void setSondageId(String sondageId) {
        this.sondageId = sondageId;
    }

    public List<AdresseEffectue> getAdresseEffectues() {
        return adresseEffectues;
    }

    public void setAdresseEffectues(List<AdresseEffectue> adresseEffectues) {
        this.adresseEffectues = adresseEffectues;
    }
}
