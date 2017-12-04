package com.shutafin.service;

import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;

import java.util.Map;

public interface EmailTemplateService {

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, Map<String, byte[]> imageSources, String emailChange);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String emailChange, String confirmationUrl);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, String link, String confirmationUrl);

}
