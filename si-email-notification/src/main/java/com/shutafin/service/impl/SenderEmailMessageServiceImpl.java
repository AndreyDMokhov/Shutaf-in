package com.shutafin.service.impl;

import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.exception.exceptions.EmailNotificationProcessingException;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.service.EmailImageSourceService;
import com.shutafin.service.EmailNotificationLogService;
import com.shutafin.service.SenderEmailMessageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@Slf4j
public class SenderEmailMessageServiceImpl implements SenderEmailMessageService {

    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";
    private static final Integer RETRIES_ON_FAILURE = 2;
    private static final Integer SECONDS_BETWEEN_RETRIES_ON_FAILURE = 2;

    private JavaMailSender mailSender;
    private EmailNotificationLogService emailNotificationLogService;
    private EmailImageSourceService emailImageSourceService;

    @Autowired
    public SenderEmailMessageServiceImpl(JavaMailSender mailSender, EmailNotificationLogService emailNotificationLogService, EmailImageSourceService emailImageSourceService) {
        this.mailSender = mailSender;
        this.emailNotificationLogService = emailNotificationLogService;
        this.emailImageSourceService = emailImageSourceService;
    }

    @Override
    public void sendEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailMessage emailMessage) {

        BaseTemplate baseTemplate = emailMessage.getMailTemplate();
        EmailTemplateHelper helper = new EmailTemplateHelper();
        String messageContent = helper.getMessageContent(baseTemplate.getTokenValueMap(), baseTemplate.getHtmlTemplate());

        EmailNotificationLog emailNotificationLog = emailNotificationLogService.get(
                baseTemplate.getEmailHeader(), emailMessage, messageContent, emailNotificationWeb.getEmailReason());

        Map<String, byte[]> imageSources = emailMessage.getImageSources();
        Set<EmailImageSource> emailImageSources = new HashSet<>();
        if (imageSources != null) {
            emailImageSources = emailImageSourceService.get(emailNotificationLog, imageSources);
        }

        MimeMessage mimeMessage = getMimeMessage(emailMessage.getEmailTo(), messageContent, baseTemplate.getEmailHeader(), imageSources);
        send(mimeMessage, emailNotificationLog, emailImageSources);
    }

    @Override
    public void sendEmailMessage(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {

        Map<String, byte[]> imageSources = new TreeMap<>();
        if (emailImageSources != null) {
            for (EmailImageSource emailImageSource : emailImageSources) {
                imageSources.put(emailImageSource.getContentId(), emailImageSource.getImageSource());
            }
        }

        MimeMessage mimeMessage = getMimeMessage(
                emailNotificationLog.getEmailTo(),
                emailNotificationLog.getEmailContent(),
                emailNotificationLog.getEmailHeader(),
                imageSources);

        send(mimeMessage, emailNotificationLog, emailImageSources);
    }

    private MimeMessage getMimeMessage(String email, String html, String header, Map<String, byte[]> imageSources) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject(header);
            mimeMessageHelper.setTo(email);
            addInlineImage(mimeMessageHelper, imageSources);
            return mimeMessage;
        } catch (MessagingException e) {
            log.error("Error occurred on MimeMessage creation!");
            log.error("MessagingException: ", e);
            throw new EmailNotificationProcessingException();
        }
    }

    private void addInlineImage(MimeMessageHelper mimeMessageHelper, Map<String, byte[]> imageSources) throws MessagingException {
        if (imageSources != null) {
            for (Map.Entry<String, byte[]> entry : imageSources.entrySet()) {
                String imageResourceName = entry.getKey();
                final InputStreamSource imageSource = new ByteArrayResource(entry.getValue());
                mimeMessageHelper.addInline(imageResourceName, imageSource, IMAGE_CONTENT_TYPE);
            }
        }
    }

    private void send(MimeMessage mimeMessage, EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {
        emailNotificationLog.setIsSendFailed(Boolean.FALSE);
        if (isSendSuccessful(mimeMessage) || isResendSuccessful(mimeMessage)) {
            updateRepositories(emailNotificationLog, emailImageSources);
            return;
        }
        emailNotificationLog.setIsSendFailed(Boolean.TRUE);
        updateRepositories(emailNotificationLog, emailImageSources);
    }

    private void updateRepositories(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {
        emailNotificationLog.setUpdatedDate(new Date());
        emailNotificationLogService.save(emailNotificationLog);
        emailImageSourceService.save(emailImageSources);
    }

    private boolean isSendSuccessful(MimeMessage mimeMessage) {
        try {
            mailSender.send(mimeMessage);
            log.info("Message sent to {}", mimeMessage.getAllRecipients()[0].toString());
            return true;
        } catch (MailException | MessagingException e) {
            log.error("Email send failed due to communication error: {}", e);
            return false;
        }
    }

    @SneakyThrows
    private boolean isResendSuccessful(MimeMessage mimeMessage) {
        log.warn("Attempting to resend failed email {}", mimeMessage.getAllRecipients()[0].toString());
        for (int i = 0; i < RETRIES_ON_FAILURE; i++) {
            log.debug("Resend attempt {} of {}", i + 1, RETRIES_ON_FAILURE);
            delay();
            if (isSendSuccessful(mimeMessage)) {
                log.info("Resend successful!");
                return true;
            }
        }
        log.error("Resend NOT successful!");
        return false;
    }

    @SneakyThrows
    private void delay() {
        log.debug("Waiting for {} seconds before resend", SECONDS_BETWEEN_RETRIES_ON_FAILURE);
        Thread.sleep(SECONDS_BETWEEN_RETRIES_ON_FAILURE * 1000);
    }

}
