package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.EmailNotificationProcessingException;
import com.shutafin.exception.exceptions.EmailSendException;
import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.repository.common.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailNotificationLogRepository emailNotificationLogRepository;

    @Override
    @Transactional
    public void sendEmail(EmailMessage emailMessage, EmailReason emailReason) {
        BaseTemplate baseTemplate = emailMessage.getMailTemplate();

        EmailTemplateHelper helper = new EmailTemplateHelper();
        String messageContent = helper.getMessageContent(baseTemplate.getTokenValueMap(), baseTemplate.getHtmlTemplate());

        String emailTo = emailMessage.getEmailTo();


        EmailNotificationLog emailNotificationLog = getEmailNotificationLog(
                messageContent,
                emailTo,
                emailMessage.getUser(),
                emailReason);

        try {
            mailSender.send(getMimeMessage(emailTo, messageContent, baseTemplate.getEmailHeader()));
        } catch (Exception e) {
            log.error("Email send exception:");
            log.error("Exception: ", e);
            emailNotificationLog.setSendFailed(Boolean.TRUE);
            emailNotificationLogRepository.update(emailNotificationLog);
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
            log.error("Email notification processing exception:");
            log.error("Exception: ", e);
            throw new EmailNotificationProcessingException();
        }
    }

    private EmailNotificationLog getEmailNotificationLog(String html, String emailTo, User user, EmailReason emailReason) {

        EmailNotificationLog emailNotificationLog = new EmailNotificationLog();

        emailNotificationLog.setEmailContent(html);

        emailNotificationLog.setEmailReason(emailReason);
        emailNotificationLog.setSendFailed(Boolean.FALSE);

        emailNotificationLog.setEmailTo(emailTo);

        emailNotificationLog.setUser(user);

        Long id = (Long) emailNotificationLogRepository.save(emailNotificationLog);
        emailNotificationLog.setId(id);
        return emailNotificationLog;
    }

}