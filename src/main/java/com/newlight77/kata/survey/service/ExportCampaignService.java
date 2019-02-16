package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.model.Campaign;
import com.newlight77.kata.survey.model.Survey;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ExportCampaignService {

  private MailService mailService;

  private static final String DEFAULT_DATE_FORMAT = "yyyyMMdd-hhmmss";
  private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

  public ExportCampaignService(MailService mailService) {
     this.mailService = mailService;
  }

  public void sendResults(Campaign campaign, Survey survey) {

    CampaignExcelBuilder builder = new CampaignExcelBuilder(campaign, survey);

    builder.buildSheet("Survey");
    builder.buildHeaderStyle();
    builder.buildHeader();
    builder.buildTitleStyle();
    builder.buildCellStyle();
    builder.buildClient();
    builder.buildSurveys();

    Workbook workbook = builder.build();

    writeFileAndSend(survey, workbook);

  }

  protected void writeFileAndSend(Survey survey, Workbook workbook) {
    try {
      File resultFile = new File(System.getProperty("java.io.tmpdir"), "survey-" + survey.getId() + "-" + dateTimeFormatter.format(LocalDateTime.now()) + ".xlsx");
      FileOutputStream outputStream = new FileOutputStream(resultFile);
      workbook.write(outputStream);

      mailService.send(resultFile);
      resultFile.deleteOnExit();
    } catch (Exception ex) {
      throw new RuntimeException("Errorr while trying to send email", ex);
    } finally {
      try {
        workbook.close();
      } catch (Exception e) {
        // CANT HAPPEN
      }
    }
  }
}
