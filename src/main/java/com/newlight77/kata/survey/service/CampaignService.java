package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.client.CampaignClient;
import com.newlight77.kata.survey.model.Campaign;
import org.springframework.stereotype.Service;

@Service
public class CampaignService {

    private CampaignClient campaignClient;

    public CampaignService(CampaignClient campaignClient) {
        this.campaignClient = campaignClient;
    }

    public void createCampaign(Campaign campaign) {
        campaignClient.createCampaign(campaign);
    }

    public Campaign getCampaign(String id) {
        return campaignClient.getCampaign(id);
    }
}
