package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.model.AddressStatus;
import com.newlight77.kata.survey.model.Campaign;
import com.newlight77.kata.survey.model.Survey;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
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

    // step 1 : extract createSheet
    Sheet sheet = createSheet(workbook);

    // step 2 : extract createHeader
    createHeader(workbook, sheet);

    // step 10 : title style
    CellStyle titleStyle = createTitleStyle(workbook);

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    // step 3 : extract createClient
    createClient(survey, sheet, titleStyle, style);

    // step 4 : extract createSurvey
    createSurveys(campaign, sheet, style);

    // step 5 : extract write and send
    writeFileAndSend(survey, workbook);

  }

  private void createSurveys(Campaign campaign, Sheet sheet, CellStyle style) {

    // step 6 : survey number
    writeSurveyNumber(campaign, sheet);

    // step 7 : survey header
    writeSurveyHeader(sheet, style);

    // step 8 : survey one by one
    writeSurveys(campaign, sheet, style);
  }

  private void writeSurveys(Campaign campaign, Sheet sheet, CellStyle style) {
    int startIndex = 9;
    int currentIndex = 0;

    for (AddressStatus addressStatus : campaign.getAddressStatuses()) {

      writeSurvey(sheet, style, startIndex, currentIndex, addressStatus);

      currentIndex++;

    }
  }

  private void writeSurvey(Sheet sheet, CellStyle style, int startIndex, int currentIndex, AddressStatus addressStatus) {
    Row surveyRow = sheet.createRow(startIndex + currentIndex);
    Cell surveyRowCell = surveyRow.createCell(0);
    surveyRowCell.setCellValue(addressStatus.getAddress().getStreetNumber());
    surveyRowCell.setCellStyle(style);

    surveyRowCell = surveyRow.createCell(1);
    surveyRowCell.setCellValue(addressStatus.getAddress().getStreetName());
    surveyRowCell.setCellStyle(style);

    surveyRowCell = surveyRow.createCell(2);
    surveyRowCell.setCellValue(addressStatus.getAddress().getPostalCode());
    surveyRowCell.setCellStyle(style);

    surveyRowCell = surveyRow.createCell(3);
    surveyRowCell.setCellValue(addressStatus.getAddress().getCity());
    surveyRowCell.setCellStyle(style);

    surveyRowCell = surveyRow.createCell(4);
    surveyRowCell.setCellValue(addressStatus.getStatus().toString());
    surveyRowCell.setCellStyle(style);
  }

  private void writeSurveyHeader(Sheet sheet, CellStyle style) {
    Row surveysHeaderRow = sheet.createRow(8);
    Cell surveyLabelCell = surveysHeaderRow.createCell(0);
    surveyLabelCell.setCellValue("N° street");
    surveyLabelCell.setCellStyle(style);

    surveyLabelCell = surveysHeaderRow.createCell(1);
    surveyLabelCell.setCellValue("streee");
    surveyLabelCell.setCellStyle(style);

    surveyLabelCell = surveysHeaderRow.createCell(2);
    surveyLabelCell.setCellValue("Postal code");
    surveyLabelCell.setCellStyle(style);

    surveyLabelCell = surveysHeaderRow.createCell(3);
    surveyLabelCell.setCellValue("City");
    surveyLabelCell.setCellStyle(style);

    surveyLabelCell = surveysHeaderRow.createCell(4);
    surveyLabelCell.setCellValue("Status");
    surveyLabelCell.setCellStyle(style);
  }

  private void writeSurveyNumber(Campaign campaign, Sheet sheet) {
    Row row;
    Cell cell;
    row = sheet.createRow(6);
    cell = row.createCell(0);
    cell.setCellValue("Number of surveys");
    cell = row.createCell(1);
    cell.setCellValue(campaign.getAddressStatuses().size());
  }

  private void createClient(Survey survey, Sheet sheet, CellStyle titleStyle, CellStyle style) {
    Row row = sheet.createRow(2);
    Cell cell = row.createCell(0);
    cell.setCellValue("Client");
    cell.setCellStyle(titleStyle);

    Row clientRow = sheet.createRow(3);
    Cell nomClientRowLabel = clientRow.createCell(0);
    nomClientRowLabel.setCellValue(survey.getClient());
    nomClientRowLabel.setCellStyle(style);

    // step 9 : client address
    createClientAddress(survey, sheet, style);
  }

  private void createClientAddress(Survey survey, Sheet sheet, CellStyle style) {
    String clientAddress = survey.getClientAddress().getStreetNumber() + " "
            + survey.getClientAddress().getStreetName() + survey.getClientAddress().getPostalCode() + " "
            + survey.getClientAddress().getCity();

    Row clientAddressLabelRow = sheet.createRow(4);
    Cell clientAddressCell = clientAddressLabelRow.createCell(0);
    clientAddressCell.setCellValue(clientAddress);
    clientAddressCell.setCellStyle(style);
  }

  private void createHeader(Workbook workbook, Sheet sheet) {
    Row header = sheet.createRow(0);

    // step 12 : header style
    CellStyle headerStyle = createHeaderStyle(workbook);

    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Survey");
    headerCell.setCellStyle(headerStyle);

  }
  
  private CellStyle createHeaderStyle(Workbook workbook) {
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 14);
    font.setBold(true);
    headerStyle.setFont(font);
    headerStyle.setWrapText(false);
    return headerStyle;
  }

  private CellStyle createTitleStyle(Workbook workbook) {
    CellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    XSSFFont titleFont = ((XSSFWorkbook) workbook).createFont();
    titleFont.setFontName("Arial");
    titleFont.setFontHeightInPoints((short) 12);
    titleFont.setUnderline(FontUnderline.SINGLE);
    titleStyle.setFont(titleFont);
    return titleStyle;
  }

  private Sheet createSheet(Workbook workbook) {
    Sheet sheet = workbook.createSheet("Survey");
    sheet.setColumnWidth(0, 10500);
    for (int i = 1; i <= 18; i++) {
      sheet.setColumnWidth(i, 6000);
    }
    return sheet;
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
