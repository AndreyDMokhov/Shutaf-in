package com.shutafin.service.impl;

import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationLogServiceImpl implements EmailNotificationLogService {

    private EmailNotificationLogRepository emailNotificationLogRepository;

    @Autowired
    public EmailNotificationLogServiceImpl(EmailNotificationLogRepository emailNotificationLogRepository) {
        this.emailNotificationLogRepository = emailNotificationLogRepository;
    }

    @Override
    public EmailNotificationLog get(String emailHeader, EmailMessage emailMessage, String html, EmailReason emailReason) {
        return emailNotificationLogRepository.save(
                EmailNotificationLog.builder()
                        .userId(emailMessage.getUserId())
                        .emailTo(emailMessage.getEmailTo())
                        .emailHeader(emailHeader)
                        .emailContent(html)
                        .emailReason(emailReason)
                        .isSendFailed(false)
                        .build());
    }

    @Override
    public EmailNotificationLog save(EmailNotificationLog emailNotificationLog) {
        return emailNotificationLogRepository.save(emailNotificationLog);
    }
}
