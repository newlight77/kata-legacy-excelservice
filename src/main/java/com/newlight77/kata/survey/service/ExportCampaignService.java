package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.model.Campaign;
import com.newlight77.kata.survey.model.Survey;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

    Workbook workbook = new XSSFWorkbook();
    CampaignExcelBuilder builder = new CampaignExcelBuilder();

    // step 1 : extract createSheet
    Sheet sheet = builder.createSheet(workbook);

    // step 2 : extract createHeader
    builder.createHeader(workbook, sheet);

    // step 10 : title style
    CellStyle titleStyle = builder.createTitleStyle(workbook);

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    // step 3 : extract createClient
    builder.createClient(survey, sheet, titleStyle, style);

    // step 4 : extract createSurvey
    builder.createSurveys(campaign, sheet, style);

    // step 5 : extract write and send
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
