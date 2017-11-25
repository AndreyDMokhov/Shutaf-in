package com.shutafin.service;

import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.smtp.EmailMessage;

public interface EmailNotificationLogService {

    EmailNotificationLog get(String emailHeader, EmailMessage emailMessage, String html, EmailReason emailReason);

    EmailNotificationLog save(EmailNotificationLog emailNotificationLog);

}
