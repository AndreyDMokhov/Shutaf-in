package com.shutafin.service;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailReason;

import java.util.Set;

public interface SenderEmailMessageService {

    void sendEmailMessage(EmailReason emailReason, EmailMessage emailMessage);

    void sendEmailMessage(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources);

}
