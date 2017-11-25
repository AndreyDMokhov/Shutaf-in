package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.smtp.EmailMessage;

import java.util.Set;

public interface SenderEmailMessageService {

    void sendEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailMessage emailMessage);

    void sendEmailMessage(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources);

}
