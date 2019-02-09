package com.newlight77.kata.survey.controler;

import com.newlight77.kata.survey.datamodel.Campaign;
import com.newlight77.kata.survey.datamodel.Survey;
import com.newlight77.kata.survey.service.ExportCampaignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SurveyController {

    private ExportCampaignService exportCampaignService;
    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);
    
    public SurveyController(final ExportCampaignService exportCampaignService) {
      this.exportCampaignService = exportCampaignService;
    }

    @RequestMapping(value = "/api/survey/create", method = RequestMethod.POST)
    public Survey createSurvey(@RequestBody Survey survey) {
        return exportCampaignService.creerSurvey(survey);
    }

    @RequestMapping(value = "/api/survey/get", method = RequestMethod.GET)
    public Survey getSurvey(@RequestBody String id) {
        return exportCampaignService.getSurvey(id);
    }

    @RequestMapping(value = "/api/survey/campaign/create", method = RequestMethod.POST)
    public Campaign createCampaign(@RequestBody Campaign campaign) {
        return exportCampaignService.createCampaign(campaign);
    }

    @RequestMapping(value = "/api/survey/campaign/get", method = RequestMethod.GET)
    public Campaign getCampaign(@RequestBody String id) {
        return exportCampaignService.getCampaign(id);
    }

    @RequestMapping(value = "/api/survey/campaign/export", method = RequestMethod.POST)
    public void exportCampaign(@RequestBody String id) {

        Campaign campaign = exportCampaignService.getCampaign(id);
        Survey survey = exportCampaignService.getSurvey(campaign.getSurveyId());

        // envoie resultats
        exportCampaignService.sendResults(campaign, survey);
        
    }
}

