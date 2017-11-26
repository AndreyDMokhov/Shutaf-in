package com.shutafin.service.impl;

import com.shutafin.service.confirmations.EmailInterface;
import com.shutafin.model.confirmations.EmailConfirmationResponse;
import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.entity.ConfirmationNewEmail;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.ConfirmationNewEmailService;
import com.shutafin.service.EmailNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Slf4j
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Autowired
    private ConfirmationNewEmailService confirmationNewEmailService;

    @Autowired
    private Map<String, EmailInterface> mapSendEmail;

    @Override
    @Transactional
    public void sendEmail(EmailNotificationWeb emailNotificationWeb) {

        EmailReason emailReason = emailNotificationWeb.getEmailReason();
        mapSendEmail.get(emailReason.getPropertyPrefix()).send(emailNotificationWeb);

    }

    @Override
    public EmailConfirmationResponse getUserIdFromConfirmation(String link) {

        ConfirmationNewEmail confirmationNewEmail = getValidLink(link);
        confirmationNewEmail.setIsConfirmed(true);
        confirmationNewEmailService.save(confirmationNewEmail);

        EmailConfirmationResponse emailConfirmationResponse = new EmailConfirmationResponse();
        emailConfirmationResponse.setUserId(confirmationNewEmail.getUserId());

        if (confirmationNewEmail.getConnectedConfirmationNewEmail() == null) {
            return emailConfirmationResponse;
        }

        ConfirmationNewEmail confirmationNewEmailConnected = confirmationNewEmail.getConnectedConfirmationNewEmail();
        if (confirmationNewEmailConnected.getIsConfirmed()) {
            emailConfirmationResponse.setNewEmail(
                    confirmationNewEmail.getNewEmail() == null ?
                            confirmationNewEmailConnected.getNewEmail()
                            :
                            confirmationNewEmail.getNewEmail());
        }
        return emailConfirmationResponse;

    }

    @Override
    public ConfirmationNewEmail getValidLink(String link) {
        ConfirmationNewEmail confirmationNewEmail = confirmationNewEmailService.getConfirmed(link);
        if (confirmationNewEmail == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }
        return confirmationNewEmail;
    }

}