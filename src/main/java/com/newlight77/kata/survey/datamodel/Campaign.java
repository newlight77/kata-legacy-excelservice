package com.newlight77.kata.survey.datamodel;

import java.util.List;

public class Campaign {

    private String id ;
    private String surveyId;
    private List<AddressStatus> addressStatuses;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<AddressStatus> getAddressStatuses() {
        return addressStatuses;
    }

    public void setAddressStatuses(List<AddressStatus> addressStatuses) {
        this.addressStatuses = addressStatuses;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
}
