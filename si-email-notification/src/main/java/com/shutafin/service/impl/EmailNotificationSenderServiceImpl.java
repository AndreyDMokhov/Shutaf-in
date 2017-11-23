package com.shutafin.service.impl;

import com.shutafin.helpers.EmailTemplateHelper;
import com.shutafin.model.email.EmailNotificationWeb;
import com.shutafin.model.email.EmailReason;
import com.shutafin.model.email.EmailResponse;
import com.shutafin.model.email.UserImageSource;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.exception.exceptions.EmailNotificationProcessingException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.repository.ConfirmationRepository;
import com.shutafin.repository.EmailImageSourceRepository;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.service.EmailNotificationSenderService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.EnvironmentConfigurationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.*;

@Service
@Slf4j
public class EmailNotificationSenderServiceImpl implements EmailNotificationSenderService {

    private static final Integer RETRIES_ON_FAILURE = 2;
    private static final Integer SECONDS_BETWEEN_RETRIES_ON_FAILURE = 2;
    private static final String IMAGE_CONTENT_TYPE = "image/jpeg";
    private static final Integer LINK_HOURS_EXPIRATION = 24;
    private static final String REGISTRATION_CONFIRMATION_URL = "/#/registration/confirmation/";
    private static final String RESET_PASSWORD_CONFIRMATION_URL = "/#/reset-password/confirmation/";
    private static final String CHANGE_EMAIL_CONFIRMATION_URL = "/#/change-email/confirmation/";
    private static final String URL_PROFILE = "/#/profile/";
    private static final String URL_SEARCH = "/#/users/search";

    private JavaMailSender mailSender;
    private EmailNotificationLogRepository emailNotificationLogRepository;
    private EmailImageSourceRepository emailImageSourceRepository;
    private EmailTemplateService emailTemplateService;
    private ConfirmationRepository confirmationRepository;
    private EnvironmentConfigurationService environmentConfigurationService;

    @Autowired
    Map<String, EmailInterface> mapSendEmail;

    @Autowired
    public EmailNotificationSenderServiceImpl(
            JavaMailSender mailSender,
            EmailNotificationLogRepository emailNotificationLogRepository,
            EmailImageSourceRepository emailImageSourceRepository,
            EmailTemplateService emailTemplateService,
            ConfirmationRepository confirmationRepository,
            EnvironmentConfigurationService environmentConfigurationService) {
        this.mailSender = mailSender;
        this.emailNotificationLogRepository = emailNotificationLogRepository;
        this.emailImageSourceRepository = emailImageSourceRepository;
        this.emailTemplateService = emailTemplateService;
        this.confirmationRepository = confirmationRepository;
        this.environmentConfigurationService = environmentConfigurationService;
    }

    @Override
    @Transactional
    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {

        EmailReason emailReason = emailNotificationWeb.getEmailReason();

        mapSendEmail.get(emailReason.getPropertyPrefix()).call(emailNotificationWeb);

    }

    public void sendEmailMatchingCandidates(EmailNotificationWeb emailNotificationWeb) {

        EmailMessage emailMessage = getMatchingCandidatesEmailMessage(emailNotificationWeb);
        sendEmailMessage(emailNotificationWeb, emailMessage);

    }

    public void sendEmailConfirmation(EmailNotificationWeb emailNotificationWeb, String confirmationUrl) {

        EmailConfirmation emailConfirmation = getConfirmation(
                emailNotificationWeb,
                null,
                null);

        EmailMessage emailMessage = getEmailMessage(emailNotificationWeb, emailConfirmation, confirmationUrl);
        sendEmailMessage(emailNotificationWeb, emailMessage);
    }

    public void sendEmailChangeEmail(EmailNotificationWeb emailNotificationWeb) {

        EmailMessage emailMessage;

        EmailConfirmation oldEmailObject = getConfirmation(
                emailNotificationWeb,
                null,
                null);

        EmailConfirmation newEmailObject = getConfirmation(
                emailNotificationWeb,
                emailNotificationWeb.getNewEmail(),
                oldEmailObject);

        oldEmailObject.setConnectedEmailConfirmation(newEmailObject);
        confirmationRepository.save(oldEmailObject);

        emailMessage = getEmailMessage(emailNotificationWeb, oldEmailObject, CHANGE_EMAIL_CONFIRMATION_URL);
        sendEmailMessage(emailNotificationWeb, emailMessage);

        emailMessage = getEmailMessage(emailNotificationWeb, newEmailObject, CHANGE_EMAIL_CONFIRMATION_URL);
        sendEmailMessage(emailNotificationWeb, emailMessage);
    }

    private void sendEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailMessage emailMessage) {

        BaseTemplate baseTemplate = emailMessage.getMailTemplate();
        EmailTemplateHelper helper = new EmailTemplateHelper();
        String messageContent = helper.getMessageContent(baseTemplate.getTokenValueMap(), baseTemplate.getHtmlTemplate());

        EmailNotificationLog emailNotificationLog = getEmailNotificationLog(baseTemplate.getEmailHeader(), emailMessage, messageContent, emailNotificationWeb.getEmailReason());

        Map<String, byte[]> imageSources = emailMessage.getImageSources();
        Set<EmailImageSource> emailImageSources = new HashSet<>();
        if (imageSources != null) {
            emailImageSources = generateEmailImageSource(emailNotificationLog, imageSources);
        }

        MimeMessage mimeMessage = getMimeMessage(emailMessage.getEmailTo(), messageContent, baseTemplate.getEmailHeader(), imageSources);
        send(mimeMessage, emailNotificationLog, emailImageSources);
    }

    private EmailConfirmation getConfirmation(
            EmailNotificationWeb emailNotificationWeb,
            String newEmail,
            EmailConfirmation connectedId) {

        EmailConfirmation confirmation = new EmailConfirmation();
        confirmation.setUserId(emailNotificationWeb.getUserId());
        confirmation.setNewEmail(newEmail);
        confirmation.setConfirmationUUID(UUID.randomUUID().toString());
        confirmation.setIsConfirmed(Boolean.FALSE);
        confirmation.setConnectedEmailConfirmation(connectedId);
        confirmation.setExpiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION));

//        EmailConfirmation emailConfirmation = EmailConfirmation.builder()
//                .userId(emailNotificationWeb.getUserId())
//                .confirmationUUID(UUID.randomUUID().toString())
//                .isConfirmed(false)
//                .expiresAt(DateUtils.addHours(new Date(), LINK_HOURS_EXPIRATION))
//                .newEmail(newEmail)
//                .connectedEmailConfirmation(connectedId)
//                .build();
        return confirmationRepository.save(confirmation);
    }

    private EmailMessage getEmailMessage(EmailNotificationWeb emailNotificationWeb, EmailConfirmation emailConfirmation, String confirmationUrl) {
        String serverAddress = environmentConfigurationService.getServerAddress();
        String urlLink = serverAddress + confirmationUrl + emailConfirmation.getConfirmationUUID();
        return emailTemplateService.getEmailMessage(emailNotificationWeb, urlLink, null, emailConfirmation.getNewEmail());
    }

    private EmailMessage getMatchingCandidatesEmailMessage(EmailNotificationWeb emailNotificationWeb) {
        String urlLink = "";
        String serverAddress = environmentConfigurationService.getServerAddress();
        Map<String, byte[]> imageSources = new TreeMap<>();

        for (UserImageSource userImageSource : emailNotificationWeb.getUserImageSources()) {
            imageSources.put(userImageSource.getUserId().toString(), userImageSource.getImageSource());
            urlLink = urlLink.concat(getUserImageLink(userImageSource, serverAddress));
        }
        urlLink += getSearchLink(serverAddress);
        return emailTemplateService.getEmailMessage(emailNotificationWeb, urlLink, imageSources);
    }

    private String getUserImageLink(UserImageSource userImageSource, String serverAddress) {
        return ""
                .concat("<p style=\"font-size:14px\"><a href=\"")
                .concat(serverAddress)
                .concat(URL_PROFILE)
                .concat(userImageSource.getUserId().toString())
                .concat("\"> ")
                .concat(userImageSource.getFirstName())
                .concat(" ")
                .concat(userImageSource.getLastName())
                .concat(" <br><img src=\"cid:")
                .concat(userImageSource.getUserId().toString())
                .concat("\" style=\"width:128px;height:128px;\">")
                .concat("</a></p>");
    }

    private String getSearchLink(String serverAddress) {
        return ""
                .concat("<p><a href=\"")
                .concat(serverAddress)
                .concat(URL_SEARCH)
                .concat("\">");
    }

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

    @Override
    public EmailResponse getUserIdFromConfirmation(String link) {

        isValidLink(link);

        EmailConfirmation emailConfirmation = confirmationRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date());

        emailConfirmation.setIsConfirmed(true);
        confirmationRepository.save(emailConfirmation);

        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setUserId(emailConfirmation.getUserId());

        if (emailConfirmation.getConnectedEmailConfirmation() == null) {
            return emailResponse;
        }

        EmailConfirmation connectedEmailConfirmation = emailConfirmation.getConnectedEmailConfirmation();
        if (connectedEmailConfirmation.getIsConfirmed()) {
            if (emailConfirmation.getNewEmail() != null){
                emailResponse.setNewEmail(emailConfirmation.getNewEmail());
            }else{
                emailResponse.setNewEmail(connectedEmailConfirmation.getNewEmail());
            }
            return emailResponse;
        }else{
            emailResponse.setUserId(null);
            return emailResponse;
        }
    }

    @Override
    public void isValidLink(String link) {
        if (confirmationRepository.findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(link, new Date()) == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }
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
        emailNotificationLog.setIsSendFailed(Boolean.TRUE);
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

    private EmailNotificationLog getEmailNotificationLog(String emailHeader, EmailMessage emailMessage, String html, EmailReason emailReason) {
        EmailNotificationLog emailNotificationLog = new EmailNotificationLog();
        emailNotificationLog.setUserId(emailMessage.getUserId());
        emailNotificationLog.setEmailTo(emailMessage.getEmailTo());
        emailNotificationLog.setEmailHeader(emailHeader);
        emailNotificationLog.setEmailContent(html);
        emailNotificationLog.setEmailReason(emailReason);
        emailNotificationLog.setIsSendFailed(Boolean.FALSE);
        return emailNotificationLogRepository.save(emailNotificationLog);
    }

    interface EmailInterface {
        void call(EmailNotificationWeb emailNotificationWeb);
    }

    @Component("registration")
    class EmailRegistration implements EmailInterface {

        @Override
        public void call(EmailNotificationWeb emailNotificationWeb) {
            sendEmailConfirmation(emailNotificationWeb, REGISTRATION_CONFIRMATION_URL);
        }
    }

    @Component("changeEmail")
    class EmailChangeEmail implements EmailInterface {

        @Override
        public void call(EmailNotificationWeb emailNotificationWeb) {
            sendEmailChangeEmail(emailNotificationWeb);
        }
    }

    @Component("resetPassword")
    class EmailResetPassword implements EmailInterface {

        @Override
        public void call(EmailNotificationWeb emailNotificationWeb) {
            sendEmailConfirmation(emailNotificationWeb, RESET_PASSWORD_CONFIRMATION_URL);
        }
    }

    @Component("matchingCandidates")
    class EmailMatchingCandidates implements EmailInterface {

        @Override
        public void call(EmailNotificationWeb emailNotificationWeb) {
            sendEmailMatchingCandidates(emailNotificationWeb);
        }
    }

}