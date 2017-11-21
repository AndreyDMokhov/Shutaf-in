package com.shutafin.service.impl;

import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.exception.exceptions.EmailNotificationProcessingException;
import com.shutafin.model.exception.exceptions.EmailSendException;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.entity.EmailReason;
import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@Slf4j
public class EmailNotificationSenderServiceImpl implements EmailNotificationSenderService {

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

        EmailNotificationLog emailNotificationLog = getEmailNotificationLog(emailMessage, messageContent, emailReason);

        try {
            mailSender.send(getMimeMessage(emailMessage.getEmailTo(), messageContent, baseTemplate.getEmailHeader()));
        } catch (MailException e) {
            log.error("Error sending email notification:", e);
            emailNotificationLog.setIsSendFailed(Boolean.TRUE);
            emailNotificationLogRepository.save(emailNotificationLog);
            throw new EmailSendException();
        }
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

    private EmailNotificationLog getEmailNotificationLog(EmailMessage emailMessage, String html, EmailReason emailReason) {
        EmailNotificationLog emailNotificationLog = new EmailNotificationLog();
        emailNotificationLog.setUserId(emailMessage.getUserId());
        emailNotificationLog.setEmailTo(emailMessage.getEmailTo());
        emailNotificationLog.setEmailContent(html);
        emailNotificationLog.setEmailReason(emailReason);
        emailNotificationLog.setIsSendFailed(Boolean.FALSE);
        return emailNotificationLogRepository.save(emailNotificationLog);
    }

}