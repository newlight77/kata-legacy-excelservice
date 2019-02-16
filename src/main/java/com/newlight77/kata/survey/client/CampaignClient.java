package com.newlight77.kata.survey.client;

import com.newlight77.kata.survey.model.Campaign;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CampaignClient {

    private WebClient webClient;

    public CampaignClient(@Value("${external.url}") String externalUrl) {
        webClient = WebClient.builder()
                .baseUrl(externalUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .build();;
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
