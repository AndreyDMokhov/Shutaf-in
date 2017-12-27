package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.EmailNotificationProcessingException;
import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.model.entities.EmailImageSource;
import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.repository.common.EmailImageSourceRepository;
import com.shutafin.repository.common.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

@Service
@Slf4j
@Deprecated
public class EmailNotificationSenderServiceImpl implements EmailNotificationSenderService {

    private static final Integer RETRIES_ON_FAILURE = 2;
    private static final Integer SECONDS_BETWEEN_RETRIES_ON_FAILURE = 2;
    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";

    private JavaMailSender mailSender;
    private EmailNotificationLogRepository emailNotificationLogRepository;
    private EmailImageSourceRepository emailImageSourceRepository;

    @Autowired
    public EmailNotificationSenderServiceImpl(
            JavaMailSender mailSender,
            EmailNotificationLogRepository emailNotificationLogRepository,
            EmailImageSourceRepository emailImageSourceRepository) {
        this.mailSender = mailSender;
        this.emailNotificationLogRepository = emailNotificationLogRepository;
        this.emailImageSourceRepository = emailImageSourceRepository;
    }

    @Override
    @Transactional
    public void sendEmail(EmailMessage emailMessage, EmailReason emailReason) {
        BaseTemplate baseTemplate = emailMessage.getMailTemplate();

        EmailTemplateHelper helper = new EmailTemplateHelper();
        String messageContent = helper.getMessageContent(baseTemplate.getTokenValueMap(), baseTemplate.getHtmlTemplate());

        String emailTo = emailMessage.getEmailTo();

        Map<String, byte[]> imageSources = emailMessage.getImageSources();

        EmailNotificationLog emailNotificationLog = generateEmailNotificationLog(
                baseTemplate.getEmailHeader(),
                messageContent,
                emailTo,
                emailMessage.getUser(),
                emailReason
        );

        Set<EmailImageSource> emailImageSources = new HashSet<>();
        if (imageSources != null){
            emailImageSources = generateEmailImageSource(emailNotificationLog, imageSources);
        }

        MimeMessage mimeMessage = getMimeMessage(emailTo, messageContent, baseTemplate.getEmailHeader(), imageSources);
        send(mimeMessage, emailNotificationLog, emailImageSources);
    }

    //resend failed email notifications
    @Override
    public void sendEmail(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {

        Map<String, byte[]> imageSources = new TreeMap<>();
        for (EmailImageSource emailImageSource : emailImageSources) {
            imageSources.put(emailImageSource.getContentId(), emailImageSource.getImageSource());
        }

        MimeMessage mimeMessage = getMimeMessage(
                emailNotificationLog.getEmailTo(),
                emailNotificationLog.getEmailContent(),
                emailNotificationLog.getEmailHeader(),
                imageSources
        );

        send(mimeMessage, emailNotificationLog, emailImageSources);
    }

    private void send(MimeMessage mimeMessage, EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {
        emailNotificationLog.setIsSendFailed(Boolean.FALSE);
        if (isSendSuccessful(mimeMessage)) {
            updateRepositories(emailNotificationLog, emailImageSources);
            return;
        }
        if (isResendSuccessful(mimeMessage)) {
            updateRepositories(emailNotificationLog, emailImageSources);
            return;
        }
        updateRepositories(emailNotificationLog, emailImageSources);
    }

    private void updateRepositories(EmailNotificationLog emailNotificationLog, Set<EmailImageSource> emailImageSources) {
        emailNotificationLogRepository.save(emailNotificationLog);
        emailImageSourceRepository.save(emailImageSources);
    }

    private boolean isSendSuccessful(MimeMessage mimeMessage) {
        try {
            mailSender.send(mimeMessage);
            log.info("Message sent to {}", mimeMessage.getAllRecipients()[0].toString());
            return true;
        } catch (MailException | MessagingException e) {
            log.error("Email send failed due to communication error: {}", e);
            return false;
        }
    }

    @SneakyThrows
    private boolean isResendSuccessful(MimeMessage mimeMessage) {
        log.warn("Attempting to resend failed email {}", mimeMessage.getAllRecipients()[0].toString());
        for (int i = 0; i < RETRIES_ON_FAILURE; i++) {
            log.debug("Resend attempt {} of {}", i + 1, RETRIES_ON_FAILURE);
            delay();
            if (isSendSuccessful(mimeMessage)) {
                log.info("Resend successful!");
                return true;
            }
        }
        log.error("Resend NOT successful!");
        return false;
    }

    @SneakyThrows
    private void delay() {
        log.debug("Waiting for {} seconds before resend", SECONDS_BETWEEN_RETRIES_ON_FAILURE);
        Thread.sleep(SECONDS_BETWEEN_RETRIES_ON_FAILURE * 1000);
    }


    private MimeMessage getMimeMessage(String email, String html, String header, Map<String, byte[]> imageSources) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject(header);
            mimeMessageHelper.setTo(email);
            addInlineImage(mimeMessageHelper, imageSources);
            return mimeMessage;

        } catch (MessagingException e) {
            log.error("Error occurred on MimeMessage creation!");
            log.error("MessagingException: ", e);
            throw new EmailNotificationProcessingException();
        }
    }

    private void addInlineImage(MimeMessageHelper mimeMessageHelper, Map<String, byte[]> imageSources) throws MessagingException {
        if (imageSources != null) {
            for (Map.Entry<String, byte[]> entry : imageSources.entrySet()) {
                String imageResourceName = entry.getKey();
                final InputStreamSource imageSource = new ByteArrayResource(entry.getValue());
                mimeMessageHelper.addInline(imageResourceName, imageSource, IMAGE_CONTENT_TYPE);
            }
        }
    }

    private EmailNotificationLog generateEmailNotificationLog(String emailHeader, String emailContent, String emailTo, User user, EmailReason emailReason) {

        EmailNotificationLog emailNotificationLog = EmailNotificationLog
                .builder()
                .emailHeader(emailHeader)
                .emailContent(emailContent)
                .emailReason(emailReason)
                .isSendFailed(Boolean.FALSE)
                .emailTo(emailTo)
                .user(user)
                .build();

        return emailNotificationLogRepository.save(emailNotificationLog);
    }

    private Set<EmailImageSource> generateEmailImageSource(EmailNotificationLog emailNotificationLog, Map<String, byte[]> imageSources) {
        Set<EmailImageSource> emailImageSources = new HashSet<>();
        for (Map.Entry<String, byte[]> entry : imageSources.entrySet()) {
            EmailImageSource emailImageSource = EmailImageSource
                    .builder()
                    .emailNotificationLog(emailNotificationLog)
                    .contentId(entry.getKey())
                    .imageSource(entry.getValue())
                    .build();
            emailImageSourceRepository.save(emailImageSource);

            emailImageSources.add(emailImageSource);
        }
        return emailImageSources;
    }

}