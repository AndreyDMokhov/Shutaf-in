package com.shutafin.service;

import com.shutafin.model.entity.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.EmailNotificationWeb;

public interface EmailTemplateService {

    BaseTemplate getTemplate(EmailReason emailReason, String languageDescription, String link);

    EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailReason emailReason);

}
