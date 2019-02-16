package com.newlight77.kata.survey.controler;

import com.newlight77.kata.survey.model.Campaign;
import com.newlight77.kata.survey.model.Survey;
import com.newlight77.kata.survey.service.CampaignService;
import com.newlight77.kata.survey.service.ExportCampaignService;
import com.newlight77.kata.survey.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class CampaignController {

    private CampaignService campaignService;
    private SurveyService surveyService;
    private ExportCampaignService exportCampaignService;
    private static final Logger logger = LoggerFactory.getLogger(CampaignController.class);

    public CampaignController(final CampaignService campaignService,
                              final SurveyService surveyService,
                              final ExportCampaignService exportCampaignService) {
      this.campaignService = campaignService;
      this.surveyService = surveyService;
      this.exportCampaignService = exportCampaignService;
    }

    @RequestMapping(value = "/api/campaigns", method = RequestMethod.POST)
    public void createCampaign(@RequestBody Campaign campaign) {
        campaignService.createCampaign(campaign);
    }

    @RequestMapping(value = "/api/campaigns", method = RequestMethod.GET)
    public Campaign getCampaign(@RequestParam String id) {
        return campaignService.getCampaign(id);
    }

    @RequestMapping(value = "/api/campaigns/export", method = RequestMethod.POST)
    public void exportCampaign(@RequestParam String campaignId) {
        Campaign campaign = campaignService.getCampaign(campaignId);
        Survey survey = surveyService.getSurvey(campaign.getSurveyId());
        exportCampaignService.sendResults(campaign, survey);
    }
}

