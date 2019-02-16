package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.client.SurveyClient;
import com.newlight77.kata.survey.model.Survey;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {

    private SurveyClient surveyClient;

    public SurveyService(SurveyClient surveyClient) {
        this.surveyClient = surveyClient;
    }

    public void creerSurvey(Survey survey) {
        surveyClient.createSurvey(survey);
    }

    public Survey getSurvey(String id) {
        return surveyClient.getSurvey(id);
    }

}
