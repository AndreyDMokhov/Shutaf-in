package com.shutafin.service.impl;

import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.service.AsyncSenderEmailService;
import com.shutafin.service.EmailImageSourceService;
import com.shutafin.service.EmailNotificationLogService;
import com.shutafin.service.SenderEmailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@Slf4j
@Transactional
public class SenderEmailMessageServiceImpl implements SenderEmailMessageService {

    private EmailNotificationLogService emailNotificationLogService;
    private EmailImageSourceService emailImageSourceService;
    private AsyncSenderEmailService asyncSenderEmailService;

    @Autowired
    public SenderEmailMessageServiceImpl(EmailNotificationLogService emailNotificationLogService, EmailImageSourceService emailImageSourceService, AsyncSenderEmailService asyncSenderEmailService) {
        this.emailNotificationLogService = emailNotificationLogService;
        this.emailImageSourceService = emailImageSourceService;
        this.asyncSenderEmailService = asyncSenderEmailService;
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

        MimeMessage mimeMessage = asyncSenderEmailService.getMimeMessage(emailMessage.getEmailTo(), messageContent, baseTemplate.getEmailHeader(), imageSources);
        asyncSenderEmailService.send(mimeMessage, emailNotificationLog, emailImageSources);
    }

    @Override
    public void sendEmailMessage(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {

        Map<String, byte[]> imageSources = new TreeMap<>();
        if (emailImageSources != null) {
            for (EmailImageSource emailImageSource : emailImageSources) {
                imageSources.put(emailImageSource.getContentId(), emailImageSource.getImageSource());
            }
        }

        MimeMessage mimeMessage = asyncSenderEmailService.getMimeMessage(
                emailNotificationLog.getEmailTo(),
                emailNotificationLog.getEmailContent(),
                emailNotificationLog.getEmailHeader(),
                imageSources);

        asyncSenderEmailService.send(mimeMessage, emailNotificationLog, emailImageSources);
    }

}
