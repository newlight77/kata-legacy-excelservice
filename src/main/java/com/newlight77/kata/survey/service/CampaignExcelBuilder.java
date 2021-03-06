package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.model.AddressStatus;
import com.newlight77.kata.survey.model.Campaign;
import com.newlight77.kata.survey.model.Survey;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CampaignExcelBuilder {

    private Campaign campaign;
    private Survey survey;
    private Workbook workbook;
    private Sheet surveySheet;
    private CellStyle headerStyle;
    private CellStyle titleStyle;
    private CellStyle cellStyle;

    private CampaignExcelBuilder(Campaign campaign, Survey survey) {
        this.campaign = campaign;
        this.survey = survey;
        workbook = new XSSFWorkbook();
    }

    public static CampaignExcelBuilder builder(Campaign campaign, Survey survey) {
        return new CampaignExcelBuilder(campaign, survey);
    }

    public CampaignExcelBuilder buildSurveyItems() {
        int startIndex = 9;
        int currentIndex = 0;

        for (AddressStatus addressStatus : campaign.getAddressStatuses()) {

            buildSurveyItem(startIndex, currentIndex, addressStatus);

            currentIndex++;

        }
        return this;
    }

    public void buildSurveyItem(int startIndex, int currentIndex, AddressStatus addressStatus) {
        Row surveyRow = surveySheet.createRow(startIndex + currentIndex);
        Cell surveyRowCell = surveyRow.createCell(0);
        surveyRowCell.setCellValue(addressStatus.getAddress().getStreetNumber());
        surveyRowCell.setCellStyle(cellStyle);

        surveyRowCell = surveyRow.createCell(1);
        surveyRowCell.setCellValue(addressStatus.getAddress().getStreetName());
        surveyRowCell.setCellStyle(cellStyle);

        surveyRowCell = surveyRow.createCell(2);
        surveyRowCell.setCellValue(addressStatus.getAddress().getPostalCode());
        surveyRowCell.setCellStyle(cellStyle);

        surveyRowCell = surveyRow.createCell(3);
        surveyRowCell.setCellValue(addressStatus.getAddress().getCity());
        surveyRowCell.setCellStyle(cellStyle);

        surveyRowCell = surveyRow.createCell(4);
        surveyRowCell.setCellValue(addressStatus.getStatus().toString());
        surveyRowCell.setCellStyle(cellStyle);
    }

    public CampaignExcelBuilder buildSurveyHeader() {
        Row surveysHeaderRow = surveySheet.createRow(8);
        Cell surveyLabelCell = surveysHeaderRow.createCell(0);
        surveyLabelCell.setCellValue("N° street");
        surveyLabelCell.setCellStyle(cellStyle);

        surveyLabelCell = surveysHeaderRow.createCell(1);
        surveyLabelCell.setCellValue("streee");
        surveyLabelCell.setCellStyle(cellStyle);

        surveyLabelCell = surveysHeaderRow.createCell(2);
        surveyLabelCell.setCellValue("Postal code");
        surveyLabelCell.setCellStyle(cellStyle);

        surveyLabelCell = surveysHeaderRow.createCell(3);
        surveyLabelCell.setCellValue("City");
        surveyLabelCell.setCellStyle(cellStyle);

        surveyLabelCell = surveysHeaderRow.createCell(4);
        surveyLabelCell.setCellValue("Status");
        surveyLabelCell.setCellStyle(cellStyle);

        return this;
    }

    public CampaignExcelBuilder buildSurveyQuantity() {
        Row row;
        Cell cell;
        row = surveySheet.createRow(6);
        cell = row.createCell(0);
        cell.setCellValue("Number of surveys");
        cell = row.createCell(1);
        cell.setCellValue(campaign.getAddressStatuses().size());

        return this;
    }

    public CampaignExcelBuilder buildClient() {
        Row row = surveySheet.createRow(2);
        Cell cell = row.createCell(0);
        cell.setCellValue("Client");
        cell.setCellStyle(titleStyle);

        Row clientRow = surveySheet.createRow(3);
        Cell nomClientRowLabel = clientRow.createCell(0);
        nomClientRowLabel.setCellValue(survey.getClient());
        nomClientRowLabel.setCellStyle(cellStyle);

        // step 9 : client address
        createClientAddress();
        return this;
    }

    public CampaignExcelBuilder createClientAddress() {
        String clientAddress = survey.getClientAddress().getStreetNumber() + " "
                + survey.getClientAddress().getStreetName() + survey.getClientAddress().getPostalCode() + " "
                + survey.getClientAddress().getCity();

        Row clientAddressLabelRow = surveySheet.createRow(4);
        Cell clientAddressCell = clientAddressLabelRow.createCell(0);
        clientAddressCell.setCellValue(clientAddress);
        clientAddressCell.setCellStyle(cellStyle);
        return this;
    }

    public CampaignExcelBuilder buildHeader() {
        Row header = surveySheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Survey");
        headerCell.setCellStyle(headerStyle);
        return this;
    }

    public CampaignExcelBuilder buildHeaderStyle() {
        headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setWrapText(false);
        return this;
    }

    public CampaignExcelBuilder buildTitleStyle() {
        titleStyle = workbook.createCellStyle();
        titleStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont titleFont = ((XSSFWorkbook) workbook).createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setUnderline(FontUnderline.SINGLE);
        titleStyle.setFont(titleFont);
        return this;
    }

    public CampaignExcelBuilder buildSheet(String sheetName) {
        surveySheet = workbook.createSheet(sheetName);
        surveySheet.setColumnWidth(0, 10500);
        for (int i = 1; i <= 18; i++) {
            surveySheet.setColumnWidth(i, 6000);
        }
        return this;
    }

    public CampaignExcelBuilder buildCellStyle() {
        cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        return this;
    }

    public Workbook build() {
        return workbook;
    }
}
