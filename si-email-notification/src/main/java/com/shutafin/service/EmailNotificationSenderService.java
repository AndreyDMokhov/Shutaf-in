package com.shutafin.service;

import com.shutafin.model.email.EmailNotificationWeb;
import com.shutafin.model.email.EmailConfirmationResponse;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;

import java.util.Set;

public interface EmailNotificationSenderService {

    void sendEmail(EmailNotificationWeb emailNotificationWeb);

    void sendEmail(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources);

    EmailConfirmationResponse getUserIdFromConfirmation(String link);

    EmailConfirmation getValidLink(String link);
}
