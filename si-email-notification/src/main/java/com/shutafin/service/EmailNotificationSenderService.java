package com.shutafin.service;

import com.shutafin.entity.smtp.EmailMessage;
import com.shutafin.entity.types.EmailReason;

public interface EmailNotificationSenderService {

    void sendEmail(EmailMessage emailMessage, EmailReason emailReason);

}
