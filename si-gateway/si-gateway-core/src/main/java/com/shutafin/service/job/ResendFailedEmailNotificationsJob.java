package com.shutafin.service.job;

import com.shutafin.model.entities.EmailImageSource;
import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.repository.common.EmailImageSourceRepository;
import com.shutafin.repository.common.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by evgeny on 10/11/2017.
 */
@Component
@Slf4j
public class ResendFailedEmailNotificationsJob {


    private EmailNotificationLogRepository emailNotificationLogRepository;
    private EmailNotificationSenderService emailNotificationSenderService;
    private EmailImageSourceRepository emailImageSourceRepository;

    @Autowired
    public ResendFailedEmailNotificationsJob(
            EmailNotificationLogRepository emailNotificationLogRepository,
            EmailNotificationSenderService emailNotificationSenderService,
            EmailImageSourceRepository emailImageSourceRepository) {
        this.emailNotificationLogRepository = emailNotificationLogRepository;
        this.emailNotificationSenderService = emailNotificationSenderService;
        this.emailImageSourceRepository = emailImageSourceRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void resendFailedEmailNotifications() {
        List<EmailNotificationLog> failedEmailNotificationLogs = emailNotificationLogRepository.findAllByIsSendFailedTrue();
        log.info("{} emails to be resend", failedEmailNotificationLogs.size());
        for (EmailNotificationLog emailNotificationLog : failedEmailNotificationLogs) {
            Set<EmailImageSource> imageSources = new HashSet<>(emailImageSourceRepository.findAllByEmailNotificationLog(emailNotificationLog));

            emailNotificationSenderService.sendEmail(emailNotificationLog, imageSources);
        }
    }

}
