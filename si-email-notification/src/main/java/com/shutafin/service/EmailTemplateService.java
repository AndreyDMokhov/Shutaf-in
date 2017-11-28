package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.entity.ConfirmationEmailChange;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;

import java.util.Map;

public interface EmailTemplateService {

    BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources, String emailChange);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String emailChange, String confirmationUrl);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String confirmationUrl);

}
