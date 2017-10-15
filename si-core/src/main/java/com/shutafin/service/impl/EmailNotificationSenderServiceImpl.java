package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.EmailNotificationProcessingException;
import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.repository.common.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Service
@Slf4j
public class EmailNotificationSenderServiceImpl implements EmailNotificationSenderService {

    private static final Integer RETRIES_ON_FAILURE = 2;
    private static final Integer SECONDS_BETWEEN_RETRIES_ON_FAILURE = 2;

    private JavaMailSender mailSender;
    private EmailNotificationLogRepository emailNotificationLogRepository;

    @Autowired
    public EmailNotificationSenderServiceImpl(
            JavaMailSender mailSender,
            EmailNotificationLogRepository emailNotificationLogRepository) {
        this.mailSender = mailSender;
        this.emailNotificationLogRepository = emailNotificationLogRepository;
    }

    @Override
    @Transactional
    public void sendEmail(EmailMessage emailMessage, EmailReason emailReason) {
        BaseTemplate baseTemplate = emailMessage.getMailTemplate();

        EmailTemplateHelper helper = new EmailTemplateHelper();
        String messageContent = helper.getMessageContent(baseTemplate.getTokenValueMap(), baseTemplate.getHtmlTemplate());

        String emailTo = emailMessage.getEmailTo();


        EmailNotificationLog emailNotificationLog = generateEmailNotificationLog(
                baseTemplate.getEmailHeader(),
                messageContent,
                emailTo,
                emailMessage.getUser(),
                emailReason
        );

        MimeMessage mimeMessage = getMimeMessage(emailTo, messageContent, baseTemplate.getEmailHeader());
        send(mimeMessage, emailNotificationLog);
    }

    //resend failed email notifications
    @Override
    public void sendEmail(EmailNotificationLog emailNotificationLog) {

        MimeMessage mimeMessage = getMimeMessage(
                emailNotificationLog.getEmailTo(),
                emailNotificationLog.getEmailContent(),
                emailNotificationLog.getEmailHeader()
        );

        send(mimeMessage, emailNotificationLog);
    }

    private void send(MimeMessage mimeMessage, EmailNotificationLog emailNotificationLog) {
        emailNotificationLog.setIsSendFailed(Boolean.FALSE);
        if (!isSendSuccessful(mimeMessage)) {
            if (!isResendSuccessful(mimeMessage)) {
                emailNotificationLog.setIsSendFailed(Boolean.TRUE);
            }
        }
        emailNotificationLogRepository.update(emailNotificationLog);
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


    private MimeMessage getMimeMessage(String email, String html, String header) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setText(html, true);
            mimeMessageHelper.setSubject(header);
            mimeMessageHelper.setTo(email);
            return mimeMessage;

        } catch (MessagingException e) {
            log.error("Error occurred on MimeMessage creation!");
            log.error("MessagingException: ", e);
            throw new EmailNotificationProcessingException();
        }
    }

    private EmailNotificationLog generateEmailNotificationLog(String emailHeader, String emailContent, String emailTo, User user, EmailReason emailReason) {

        EmailNotificationLog emailNotificationLog = new EmailNotificationLog();

        emailNotificationLog.setEmailHeader(emailHeader);
        emailNotificationLog.setEmailContent(emailContent);

        emailNotificationLog.setEmailReason(emailReason);
        emailNotificationLog.setIsSendFailed(Boolean.FALSE);

        emailNotificationLog.setEmailTo(emailTo);

        emailNotificationLog.setUser(user);

        Long id = (Long) emailNotificationLogRepository.save(emailNotificationLog);
        emailNotificationLog.setId(id);
        return emailNotificationLog;
    }

}