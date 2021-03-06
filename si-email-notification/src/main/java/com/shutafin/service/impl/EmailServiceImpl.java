package com.shutafin.service.impl;

import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.model.exception.exceptions.EmailResendIntervalException;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.EmailNotificationDealWeb;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
import com.shutafin.model.web.email.EmailResendWeb;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.service.EmailService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import com.shutafin.service.sender.BaseEmailDealInterface;
import com.shutafin.service.sender.BaseEmailInterface;
import com.shutafin.service.validation.BaseValidationLinkInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final String VALIDATION_BEAN_NAME_SUFFIX = "ValidationLink";
    private static final String CONFIRMATION_BEAN_NAME_SUFFIX = "ConfirmationResponse";
    private static final int INTERVAL_RESEND_EMAIL_MINUTES = 2;

    @Autowired
    private Map<String, BaseEmailInterface> senderEmailsMap;

    @Autowired
    private Map<String, BaseEmailDealInterface> senderEmailsDealMap;

    @Autowired
    private Map<String, BaseValidationLinkInterface> validationLinksMap;

    @Autowired
    private EmailNotificationLogRepository emailNotificationLogRepository;

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    private Map<String, BaseConfirmationResponseInterface> confirmationResponsesMap;

    @Override
    public void sendEmails(EmailNotificationWeb emailNotificationWeb) {

        String emailReasonPrefix = emailNotificationWeb.getEmailReason().getPropertyPrefix();
        BaseEmailInterface sendEmail = senderEmailsMap.get(emailReasonPrefix);
        if (sendEmail == null) {
            printError("senderEmailsMap", emailReasonPrefix);
            throw new ResourceNotFoundException();
        }

        sendEmail.send(emailNotificationWeb);
    }

    @Override
    public void sendEmails(EmailNotificationDealWeb emailNotificationDealWeb) {

        String emailReasonPrefix = emailNotificationDealWeb.getEmailReason().getPropertyPrefix();
        BaseEmailDealInterface sendEmail = senderEmailsDealMap.get(emailReasonPrefix);
        if (sendEmail == null) {
            printError("senderEmailsDealMap", emailReasonPrefix);
            throw new ResourceNotFoundException();
        }

        sendEmail.send(emailNotificationDealWeb);
    }

    @Override
    public Object getValidLink(String link, EmailReason emailReason) {

        String emailReasonPrefix = emailReason.getPropertyPrefix() + VALIDATION_BEAN_NAME_SUFFIX;
        BaseValidationLinkInterface validationBean = validationLinksMap.get(emailReasonPrefix);
        if (validationBean == null) {
            printError("validationLinksMap", emailReasonPrefix);
            throw new ResourceNotFoundException();
        }

        return validationBean.validate(link);
    }

    @Override
    public Object getConfirmationResponse(String link, EmailReason emailReason) {

        String emailReasonPrefix = emailReason.getPropertyPrefix() + CONFIRMATION_BEAN_NAME_SUFFIX;
        BaseConfirmationResponseInterface confirmationBean = confirmationResponsesMap.get(emailReasonPrefix);
        if (confirmationBean == null) {
            printError("confirmationResponsesMap", emailReasonPrefix);
            throw new ResourceNotFoundException();
        }

        return confirmationBean.getResponse(link);
    }

    @Override
    public void revertConfirmation(String link, EmailReason emailReason) {
        String emailReasonPrefix = emailReason.getPropertyPrefix() + CONFIRMATION_BEAN_NAME_SUFFIX;
        BaseConfirmationResponseInterface confirmationBean = confirmationResponsesMap.get(emailReasonPrefix);
        if (confirmationBean == null) {
            printError("confirmationResponsesMap", emailReasonPrefix);
            throw new ResourceNotFoundException();
        }

        confirmationBean.revertConfirmation(link);
    }

    @Override
    public void resendEmails(EmailResendWeb emailResendWeb) {

        EmailNotificationLog emailNotificationLog = emailNotificationLogRepository
                .findFirstByEmailToAndEmailReason(emailResendWeb.getEmailTo(), emailResendWeb.getEmailReason());
        if (emailNotificationLog == null) {
            log.warn("Resource not found exception:");
            log.warn("resendEmails: emailNotificationLog with {} and {} was not found",
                    emailResendWeb.getEmailTo(), emailResendWeb.getEmailReason());
            throw new ResourceNotFoundException();
        }
        if (emailResendWeb.getEmailReason() == EmailReason.REGISTRATION) {
            emailNotificationLog.setUpdatedDate(new Date());
        }

        if (DateUtils.addMinutes(emailNotificationLog.getUpdatedDate(), INTERVAL_RESEND_EMAIL_MINUTES).after(new Date())) {
            log.warn("resendEmails: interval of resend email {} minutes", INTERVAL_RESEND_EMAIL_MINUTES);
            throw new EmailResendIntervalException();
        }
        senderEmailMessageService.sendEmailMessage(emailNotificationLog, null);
    }

    private void printError(String mapName, String emailReasonPrefix) {
        log.warn("Resource not found exception:");
        log.warn("{} was not found component {}", mapName, emailReasonPrefix);

    }

}