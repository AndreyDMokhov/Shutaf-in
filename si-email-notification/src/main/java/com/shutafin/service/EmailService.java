package com.shutafin.service;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;

public interface EmailService {

    void sendEmail(EmailNotificationWeb emailNotificationWeb);

    Object getConfirmationResponse(String link, EmailReason emailReason);

    Object getValidLink(String link, EmailReason emailReason);
}
