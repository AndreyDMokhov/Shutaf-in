package com.shutafin.service.sender.registration;

import com.shutafin.model.entity.confirmation.registration.ConfirmationRegistration;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmation.ConfirmationRegistrationService;
import com.shutafin.service.sender.BaseEmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("registration")
public class SenderRegistrationComponent implements BaseEmailInterface {

    private static final String REGISTRATION_CONFIRMATION_URL = "/#/users/registration/confirmation/";

    @Autowired
    private ConfirmationRegistrationService confirmationRegistrationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    public void send(EmailNotificationWeb emailNotificationWeb) {

        ConfirmationRegistration confirmationRegistration = confirmationRegistrationService.get(emailNotificationWeb);
        confirmationRegistrationService.save(confirmationRegistration);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, confirmationRegistration.getConfirmationUUID(), REGISTRATION_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb.getEmailReason(), emailMessage);
    }
}
