package com.newlight77.kata.excel.controler;

import com.newlight77.kata.excel.datamodel.Campagne;
import com.newlight77.kata.excel.datamodel.Sondage;
import com.newlight77.kata.excel.service.ExportCampagneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SondageController {

    private ExportCampagneService exportCampagneService;
    private static final Logger logger = LoggerFactory.getLogger(SondageController.class);
    
    public SondageController(final ExportCampagneService exportCampagneService) {
      this.exportCampagneService = exportCampagneService;
    }

    @RequestMapping(value = "/api/sondage/creer", method = RequestMethod.POST)
    public Sondage creerSondage(@RequestBody Sondage sondage) {
        return exportCampagneService.creerSondage(sondage);
    }

    @RequestMapping(value = "/api/sondage/get", method = RequestMethod.GET)
    public Sondage getSondage(@RequestBody String id) {
        return exportCampagneService.getSondage(id);
    }

    @RequestMapping(value = "/api/sondage/campagne/creer", method = RequestMethod.POST)
    public Campagne creerCampagne(@RequestBody Campagne campagne) {
        return exportCampagneService.creerCampagne(campagne);
    }

    @RequestMapping(value = "/api/sondage/campagne/get", method = RequestMethod.GET)
    public Campagne getCampagne(@RequestBody String id) {
        return exportCampagneService.getCampagne(id);
    }

    @RequestMapping(value = "/api/sondage/campagne/export", method = RequestMethod.POST)
    public void exportCampagne(@RequestBody String id) {

        Campagne campagne = exportCampagneService.getCampagne(id);
        Sondage sondage = exportCampagneService.getSondage(campagne.getSondageId());

        // envoie resultats
        exportCampagneService.sendResults(campagne, sondage);
        
    }
}

