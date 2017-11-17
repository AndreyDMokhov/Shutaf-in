package com.shutafin.service;

import com.shutafin.entity.smtp.BaseTemplate;
import com.shutafin.entity.smtp.EmailMessage;
import com.shutafin.entity.types.EmailReason;
import com.shutafin.entity.web.EmailNotificationWeb;

public interface EmailTemplateService {

    BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link);
    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailReason emailReason);
}
