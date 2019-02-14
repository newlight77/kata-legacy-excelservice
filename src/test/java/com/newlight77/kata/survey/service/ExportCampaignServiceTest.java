package com.newlight77.kata.survey.service;

import com.newlight77.kata.survey.client.CampaignClient;
import com.newlight77.kata.survey.model.Campaign;
import com.newlight77.kata.survey.model.Survey;
import com.newlight77.kata.survey.util.JsonUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.File;

public class ExportCampaignServiceTest {

    private ExportCampaignService service;

    @Mock
    private CampaignClient campaignClientMock;

    @Mock
    private MailService mailServiceMock;

    @Captor
    private ArgumentCaptor<Survey> surveyCaptor;

    @Captor
    private ArgumentCaptor<Workbook> workBookCaptor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        service = new ExportCampaignService(campaignClientMock, mailServiceMock);
        service = Mockito.spy(service);
    }

    @Test
    public void test() {

        // Arrange
        Survey survey = JsonUtil.instance().fromJsonFile("/survey.json", Survey.class);
        Campaign campaign = JsonUtil.instance().fromJsonFile("/campaign.json", Campaign.class);

        Mockito.doReturn(campaign).when(campaignClientMock).getCampaign(Mockito.anyString());
        Mockito.doNothing().when(mailServiceMock).send(Mockito.any(File.class));

        // Act
        service.sendResults(campaign, survey);

        // Assert
        Mockito.verify(service).writeFileAndSend(surveyCaptor.capture(), workBookCaptor.capture());

        Workbook workbook = workBookCaptor.getValue();

        Assertions.assertThat(workbook).isNotNull();

        Sheet sheet = workbook.getSheet("Survey");
        Assertions.assertThat(sheet).isNotNull();

        assertSurveySheetContent(survey, campaign, sheet);

    }

    private void assertSurveySheetContent(Survey survey, Campaign campaign, Sheet sheet) {
        Row headerRow = sheet.getRow(0);
        Assertions.assertThat(headerRow).isNotNull();
        Assertions.assertThat(headerRow.getCell(0)).isNotNull();
        Assertions.assertThat(headerRow.getCell(0).getStringCellValue()).isEqualTo("Survey");

        Row clientRow = sheet.getRow(2);
        Assertions.assertThat(clientRow).isNotNull();
        Assertions.assertThat(clientRow.getCell(0)).isNotNull();
        Assertions.assertThat(clientRow.getCell(0).getStringCellValue()).isEqualTo("Client");

        Row clientNameRow = sheet.getRow(3);
        Assertions.assertThat(clientNameRow).isNotNull();
        Assertions.assertThat(clientNameRow.getCell(0)).isNotNull();
        Assertions.assertThat(clientNameRow.getCell(0).getStringCellValue()).isEqualTo(survey.getClient());

        Row clientAddressRow = sheet.getRow(4);
        Assertions.assertThat(clientAddressRow).isNotNull();
        Assertions.assertThat(clientAddressRow.getCell(0)).isNotNull();

        Row SurveyNumberRow = sheet.getRow(6);
        Assertions.assertThat(SurveyNumberRow).isNotNull();
        Assertions.assertThat(SurveyNumberRow.getCell(0)).isNotNull();
        Assertions.assertThat(SurveyNumberRow.getCell(0).getStringCellValue()).isEqualTo("Number of surveys");
        Assertions.assertThat(SurveyNumberRow.getCell(1)).isNotNull();
        Assertions.assertThat(SurveyNumberRow.getCell(1).getNumericCellValue()).isEqualTo(campaign.getAddressStatuses().size());

        Row surveysHeaderRow = sheet.getRow(8);
        Assertions.assertThat(surveysHeaderRow).isNotNull();
    }
}
