package com.newlight77.kata.survey.webservice;

import com.newlight77.kata.survey.datamodel.Campaign;
import com.newlight77.kata.survey.datamodel.Survey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CampaignWebService {

    private WebClient webClient;

    public CampaignWebService(@Value("external.api") String externalUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(externalUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();;
    }

    public Survey creerSurvey(Survey survey) {
        return webClient.post()
                .uri("/survey")
                .syncBody(survey)
                .retrieve()
                .bodyToMono(Survey.class).block();
    }

    public Survey getSurvey(String id) {
        return webClient.get()
                .uri("/survey/" + id)
                .retrieve()
                .bodyToMono(Survey.class).block();
    }

    public Campaign createCampaign(Campaign campaign) {
        return webClient.post()
                .uri("/campaign")
                .syncBody(campaign)
                .retrieve()
                .bodyToMono(Campaign.class).block();
    }

    public Campaign getCampaign(String id) {
        return webClient.get()
                .uri("/campaign/" + id)
                .retrieve()
                .bodyToMono(Campaign.class).block();
    }
}
