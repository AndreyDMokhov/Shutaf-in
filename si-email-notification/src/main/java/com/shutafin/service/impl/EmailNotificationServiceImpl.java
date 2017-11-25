package com.shutafin.service.impl;

import com.shutafin.service.confirmations.EmailInterface;
import com.shutafin.model.confirmations.EmailConfirmationResponse;
import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.EmailConfirmationService;
import com.shutafin.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private EmailConfirmationService emailConfirmationService;

    @Autowired
    private Map<String, EmailInterface> mapSendEmail;

    @Autowired
    public EmailNotificationServiceImpl(EmailConfirmationService emailConfirmationService) {
        this.emailConfirmationService = emailConfirmationService;
    }

    @Override
    @Transactional
    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {

        EmailReason emailReason = emailNotificationWeb.getEmailReason();
        mapSendEmail.get(emailReason.getPropertyPrefix()).send(emailNotificationWeb);

    }

    @Override
    public EmailConfirmationResponse getUserIdFromConfirmation(String link) {

        EmailConfirmation emailConfirmation = getValidLink(link);
        emailConfirmation.setIsConfirmed(true);
        emailConfirmationService.save(emailConfirmation);

        EmailConfirmationResponse emailConfirmationResponse = new EmailConfirmationResponse();
        emailConfirmationResponse.setUserId(emailConfirmation.getUserId());

        if (emailConfirmation.getConnectedEmailConfirmation() == null) {
            return emailConfirmationResponse;
        }

        EmailConfirmation emailConfirmationConnected = emailConfirmation.getConnectedEmailConfirmation();
        if (emailConfirmationConnected.getIsConfirmed()) {
            emailConfirmationResponse.setNewEmail(
                    emailConfirmation.getNewEmail() == null ?
                            emailConfirmationConnected.getNewEmail()
                            :
                            emailConfirmation.getNewEmail());
        }
        return emailConfirmationResponse;

    }

    @Override
    public EmailConfirmation getValidLink(String link) {
        EmailConfirmation emailConfirmation = emailConfirmationService.getConfirmed(link);
        if (emailConfirmation == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }
        return emailConfirmation;
    }

}