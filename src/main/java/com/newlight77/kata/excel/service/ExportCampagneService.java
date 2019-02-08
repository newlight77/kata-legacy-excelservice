package com.newlight77.kata.excel.service;

import com.newlight77.kata.excel.datamodel.AdresseEffectue;
import com.newlight77.kata.excel.datamodel.Campagne;
import com.newlight77.kata.excel.datamodel.Sondage;
import com.newlight77.kata.excel.mail.MailService;
import com.newlight77.kata.excel.webservice.SondageWebService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class ExportCampagneService {

  private SondageWebService sondageWebService;
  private MailService mailService;
  private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
  
  public ExportCampagneService(final SondageWebService campagneWebService, MailService mailService) {
    this.sondageWebService = campagneWebService;
    this.mailService = mailService;
  }

  public Sondage creerSondage(Sondage sondage) {
    sondage.setId(UUID.randomUUID().toString());
    return sondage;
  }

  public Sondage getSondage(String id) {
    return sondageWebService.getSondage(id);
  }

  public Campagne creerCampagne(Campagne campagne) {
    return sondageWebService.creerCampagne(campagne);
  }

  public Campagne getCampagne(String id) {
    return sondageWebService.getCampagne(id);
  }

  public void sendResults(Campagne campagne, Sondage sondage) {
    Workbook workbook = new XSSFWorkbook();

    // une feuille de sondage sommaire pour un cleint
    Sheet sheet = workbook.createSheet("Sondage");
    sheet.setColumnWidth(0, 10500);
    for (int i = 1; i <= 18; i++) {
      sheet.setColumnWidth(i, 6000);
    }    

    // 1ere ligne =  l'entête
    Row header = sheet.createRow(0);
     
    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
     
    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 14);
    font.setBold(true);
    headerStyle.setFont(font);
    headerStyle.setWrapText(false);
    
    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Sondage");
    headerCell.setCellStyle(headerStyle);
    
    CellStyle titleStyle = workbook.createCellStyle();
    titleStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
    titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    XSSFFont titleFont = ((XSSFWorkbook) workbook).createFont();
    titleFont.setFontName("Arial");
    titleFont.setFontHeightInPoints((short) 12);
    titleFont.setUnderline(FontUnderline.SINGLE);
    titleStyle.setFont(titleFont);    
    
    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    // section client
    Row row = sheet.createRow(2);
    Cell cell = row.createCell(0);
    cell.setCellValue("Client");
    cell.setCellStyle(titleStyle);

    Row nomClientRow = sheet.createRow(3);
    Cell nomClientRowLabel = nomClientRow.createCell(0);
    nomClientRowLabel.setCellValue("Nom du client");
    nomClientRowLabel.setCellStyle(style);
    Cell nomClientRowValue = nomClientRow.createCell(1);
    nomClientRowValue.setCellValue(sondage.getClient());
    nomClientRowValue.setCellStyle(style);

    Row adresseClientLabelRow = sheet.createRow(4);
    Cell adresseClientRowLabel = adresseClientLabelRow.createCell(0);
    adresseClientRowLabel.setCellValue("Adresse client");
    adresseClientRowLabel.setCellStyle(style);

    String adresseClient = sondage.getAdresseClient().getNumVoie() + " "
            + sondage.getAdresseClient().getNomVoie() + sondage.getAdresseClient().getCodePostal() + " "
            + sondage.getAdresseClient().getCommune();

    Row adresseClientValueRow1 = sheet.createRow(5);
    Cell adresseClientRowValue1 = adresseClientValueRow1.createCell(1);
    adresseClientRowValue1.setCellValue(adresseClient);
    adresseClientRowValue1.setCellStyle(style);


    row = sheet.createRow(7);
    cell = row.createCell(0);
    cell.setCellValue("Les adresses de sondage");
    cell.setCellStyle(titleStyle);

    row = sheet.createRow(8);
    cell = row.createCell(0);
    cell.setCellValue("Nombre de sondage effectués");
    cell = row.createCell(1);
    cell.setCellValue(campagne.getAdresseEffectues().size());

    Row sondageEffectueLabel = sheet.createRow(8);
    Cell sondageEffectueLabelCell = sondageEffectueLabel.createCell(0);
    sondageEffectueLabelCell.setCellValue("N° voie");
    sondageEffectueLabelCell.setCellStyle(style);

    sondageEffectueLabelCell = sondageEffectueLabel.createCell(1);
    sondageEffectueLabelCell.setCellValue("Nom de voie");
    sondageEffectueLabelCell.setCellStyle(style);

    sondageEffectueLabelCell = sondageEffectueLabel.createCell(2);
    sondageEffectueLabelCell.setCellValue("Code postal");
    sondageEffectueLabelCell.setCellStyle(style);

    sondageEffectueLabelCell = sondageEffectueLabel.createCell(3);
    sondageEffectueLabelCell.setCellValue("Commune");
    sondageEffectueLabelCell.setCellStyle(style);

    sondageEffectueLabelCell = sondageEffectueLabel.createCell(4);
    sondageEffectueLabelCell.setCellValue("Statut");
    sondageEffectueLabelCell.setCellStyle(style);

    int startIndex = 9;
    int currentIndex = 0;

    for (AdresseEffectue adresseEffectue : campagne.getAdresseEffectues()) {

      Row sondageEffectueRow = sheet.createRow(startIndex + currentIndex);
      Cell sondageEffectueCell = sondageEffectueRow.createCell(0);
      sondageEffectueCell.setCellValue(adresseEffectue.getAdresse().getNumVoie());
      sondageEffectueCell.setCellStyle(style);

      sondageEffectueCell = sondageEffectueRow.createCell(1);
      sondageEffectueCell.setCellValue(adresseEffectue.getAdresse().getNomVoie());
      sondageEffectueCell.setCellStyle(style);

      sondageEffectueCell = sondageEffectueRow.createCell(2);
      sondageEffectueCell.setCellValue(adresseEffectue.getAdresse().getCodePostal());
      sondageEffectueCell.setCellStyle(style);

      sondageEffectueCell = sondageEffectueRow.createCell(3);
      sondageEffectueCell.setCellValue(adresseEffectue.getAdresse().getCommune());
      sondageEffectueCell.setCellStyle(style);

      sondageEffectueCell = sondageEffectueRow.createCell(2);
      sondageEffectueCell.setCellValue(adresseEffectue.getStatut().toString());
      sondageEffectueCell.setCellStyle(style);

      currentIndex++;

    }

    try {
      File resultFile = new File(System.getProperty("java.io.tmpdir"), "sondage-" + sondage.getId() + "-" + dateTimeFormatter.format(LocalDate.now()) + ".xlsx");
      FileOutputStream outputStream = new FileOutputStream(resultFile);
      workbook.write(outputStream);   
      
      mailService.send(resultFile);
      resultFile.deleteOnExit();
    } catch(Exception ex) {
        throw new RuntimeException("Erreur lors de l'envoie du mail", ex);
    } finally {
      try { 
        workbook.close(); 
      } catch(Exception e) {
        // CANT HAPPEN
      }
    }
    
  }

}
