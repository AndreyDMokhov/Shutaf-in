package com.shutafin.service;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.EmailResendWeb;

public interface EmailService {

    void sendEmail(EmailNotificationWeb emailNotificationWeb);

    Object getConfirmationResponse(String link, EmailReason emailReason);

    Object getValidLink(String link, EmailReason emailReason);

    void resendEmail(EmailResendWeb emailResendWeb);
}
