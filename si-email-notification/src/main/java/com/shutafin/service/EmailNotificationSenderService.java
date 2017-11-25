package com.shutafin.service;

import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.entity.EmailReason;

public interface EmailNotificationSenderService {

    void sendEmail(EmailMessage emailMessage, EmailReason emailReason);

}
