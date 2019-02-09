package com.newlight77.kata.excel.webservice;

import com.newlight77.kata.excel.datamodel.Campagne;
import com.newlight77.kata.excel.datamodel.Sondage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SondageWebService {

    private WebClient webClient;

    public SondageWebService(@Value("external.api") String externalUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(externalUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();;
    }

    public Sondage creerSondage(Sondage sondage) {
        return webClient.post()
                .uri("/sondage")
                .syncBody(sondage)
                .retrieve()
                .bodyToMono(Sondage.class).block();
    }

    public Sondage getSondage(String id) {
        return webClient.get()
                .uri("/sondage/" + id)
                .retrieve()
                .bodyToMono(Sondage.class).block();
    }

    public Campagne creerCampagne(Campagne campagne) {
        return webClient.post()
                .uri("/campagne")
                .syncBody(campagne)
                .retrieve()
                .bodyToMono(Campagne.class).block();
    }

    public Campagne getCampagne(String id) {
        return webClient.get()
                .uri("/campagne/" + id)
                .retrieve()
                .bodyToMono(Campagne.class).block();
    }
}
