package com.shutafin.service;

import com.shutafin.model.confirmations.EmailConfirmationResponse;
import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.EmailConfirmation;

public interface EmailNotificationService {

    void sendEmail(EmailNotificationWeb emailNotificationWeb);

    EmailConfirmationResponse getUserIdFromConfirmation(String link);

    EmailConfirmation getValidLink(String link);
}
