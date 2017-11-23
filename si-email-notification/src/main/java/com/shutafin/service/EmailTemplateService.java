package com.shutafin.service;

import com.shutafin.model.email.EmailNotificationWeb;
import com.shutafin.model.email.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;

import java.util.Map;

public interface EmailTemplateService {

    BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources, String newEmail);

}
