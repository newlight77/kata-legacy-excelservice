package com.newlight77.kata.excel.mail;

import com.newlight77.kata.excel.config.MailServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailService {
  private JavaMailSender mailSender;
  private MailServiceConfiguration mailServiceConfig;
  private static final Logger logger = LoggerFactory.getLogger(MailService.class);

  public MailService(final JavaMailSender mailSender, final MailServiceConfiguration mailServiceConfig) {
    this.mailSender = mailSender;
    this.mailServiceConfig = mailServiceConfig;
  }

  public void send(File attachment) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
      messageHelper.setFrom(mailServiceConfig.getFrom());
      messageHelper.setTo(mailServiceConfig.getTo());
      messageHelper.setSubject("Résultat de la campagne");
      messageHelper.setText("Bonjour,\n\nEn pièce jointe le résultat de la campagne.");

      FileSystemResource file = new FileSystemResource(attachment);
      messageHelper.addAttachment(file.getFilename(), file);
    };
    try {
      mailSender.send(messagePreparator);
      logger.info("Mail envoyé avec succès");
    } catch (MailException e) {
      throw new RuntimeException("Erreur lors de l'envoie du mail", e);
    }
  }

}
