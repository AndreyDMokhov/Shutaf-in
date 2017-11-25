package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;

import java.util.Map;

public interface EmailTemplateService {

    BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources, String newEmail);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailConfirmation emailConfirmation, String confirmationUrl);

}
