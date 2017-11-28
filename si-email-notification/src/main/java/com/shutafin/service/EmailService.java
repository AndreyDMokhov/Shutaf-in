package com.shutafin.service;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;

public interface EmailService {

    void sendEmail(EmailNotificationWeb emailNotificationWeb);

    Object getConfirmationResponse(String link, EmailReason emailReason);

    Object getValidLink(String link, EmailReason emailReason);
}
