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

    public CampaignWebService(@Value("${external.url}") String externalUrl) {
        webClient = WebClient.builder()
                .baseUrl(externalUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();;
    }

    public void createSurvey(Survey survey) {
        webClient.post()
                .uri("/surveys")
                .syncBody(survey)
                .retrieve();
    }

    public Survey getSurvey(String id) {
        return webClient.get()
                .uri("/surveys/" + id)
                .retrieve()
                .bodyToMono(Survey.class).block();
    }

    public void createCampaign(Campaign campaign) {
        webClient.post()
                .uri("/campaigns")
                .syncBody(campaign);
    }

    public Campaign getCampaign(String id) {
        return webClient.get()
                .uri("/campaigns/" + id)
                .retrieve()
                .bodyToMono(Campaign.class).block();
    }
}
