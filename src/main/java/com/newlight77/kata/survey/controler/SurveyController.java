package com.newlight77.kata.survey.controler;

import com.newlight77.kata.survey.model.Survey;
import com.newlight77.kata.survey.service.ExportCampaignService;
import com.newlight77.kata.survey.service.SurveyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class SurveyController {

    private SurveyService surveyService;
    private ExportCampaignService exportCampaignService;
    private static final Logger logger = LoggerFactory.getLogger(SurveyController.class);
    
    public SurveyController(final SurveyService surveyService, final ExportCampaignService exportCampaignService) {
        this.surveyService = surveyService;
      this.exportCampaignService = exportCampaignService;
    }

    @RequestMapping(value = "/api/surveys", method = RequestMethod.POST)
    public void createSurvey(@RequestBody Survey survey) {
        surveyService.creerSurvey(survey);
    }

    @RequestMapping(value = "/api/surveys", method = RequestMethod.GET)
    public Survey getSurvey(@RequestParam String id) {
        return surveyService.getSurvey(id);
    }

}

