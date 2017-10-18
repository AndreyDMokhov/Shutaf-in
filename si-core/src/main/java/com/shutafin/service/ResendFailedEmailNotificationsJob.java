package com.shutafin.service;

import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.repository.common.EmailNotificationLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by evgeny on 10/11/2017.
 */
@Component
@Slf4j
public class ResendFailedEmailNotificationsJob {

    @Autowired
    private EmailNotificationLogRepository emailNotificationLogRepository;

    @Autowired
    private EmailNotificationSenderService emailNotificationSenderService;

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void resendFailedEmailNotifications() {
        List<EmailNotificationLog> failedEmailNotificationLogs = emailNotificationLogRepository.getAllFailedEmailNotifications();
        log.info("{} emails to be resend", failedEmailNotificationLogs.size());
        for (EmailNotificationLog emailNotificationLog : failedEmailNotificationLogs) {
            emailNotificationSenderService.sendEmail(emailNotificationLog);
        }
    }

}
