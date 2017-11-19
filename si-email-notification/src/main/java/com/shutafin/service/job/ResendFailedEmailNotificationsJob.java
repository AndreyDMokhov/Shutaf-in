package com.shutafin.service.job;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.repository.EmailImageSourceRepository;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
