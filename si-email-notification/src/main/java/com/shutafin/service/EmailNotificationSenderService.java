package com.shutafin.service;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.entity.EmailReason;
import com.shutafin.model.smtp.EmailMessage;

import java.util.Set;

public interface EmailNotificationSenderService {

    void sendEmail(EmailMessage emailMessage, EmailReason emailReason);

    void sendEmail(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources);

}
