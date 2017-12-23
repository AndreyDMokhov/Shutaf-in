package com.shutafin.service.job;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.repository.EmailImageSourceRepository;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.service.SenderEmailMessageService;
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
    private SenderEmailMessageService senderEmailMessageService;
    private EmailImageSourceRepository emailImageSourceRepository;

    @Autowired
    public ResendFailedEmailNotificationsJob(EmailNotificationLogRepository emailNotificationLogRepository, SenderEmailMessageService senderEmailMessageService, EmailImageSourceRepository emailImageSourceRepository) {
        this.emailNotificationLogRepository = emailNotificationLogRepository;
        this.senderEmailMessageService = senderEmailMessageService;
        this.emailImageSourceRepository = emailImageSourceRepository;
    }

    @Transactional
    @Scheduled(fixedRate = 30000)
    public void resendFailedEmailNotifications() {
        List<EmailNotificationLog> failedEmailNotificationLogs = emailNotificationLogRepository.findAllByIsSendFailedTrue();
        log.info("{} emails to be resend", failedEmailNotificationLogs.size());
        for (EmailNotificationLog emailNotificationLog : failedEmailNotificationLogs) {
            Set<EmailImageSource> imageSources = new HashSet<>(emailImageSourceRepository.findAllByEmailNotificationLog(emailNotificationLog));
            senderEmailMessageService.sendEmailMessage(emailNotificationLog, imageSources);
        }
    }

}
