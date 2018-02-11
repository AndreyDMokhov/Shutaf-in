package com.shutafin.service;

import com.shutafin.model.web.email.EmailNotificationDealWeb;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.EmailResendWeb;

public interface EmailService {

    void sendEmails(EmailNotificationWeb emailNotificationWeb);

    void sendEmails(EmailNotificationDealWeb emailNotificationDealWeb);

    Object getConfirmationResponse(String link, EmailReason emailReason);

    Object getValidLink(String link, EmailReason emailReason);

    void resendEmails(EmailResendWeb emailResendWeb);
}
