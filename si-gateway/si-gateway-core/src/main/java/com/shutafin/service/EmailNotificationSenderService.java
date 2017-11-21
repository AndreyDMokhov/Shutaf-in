package com.shutafin.service;

import com.shutafin.model.entities.EmailImageSource;
import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;

import java.util.Set;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public interface EmailNotificationSenderService {

    void sendEmail(EmailMessage emailMessage, EmailReason emailReason);
    void sendEmail(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources);
}
