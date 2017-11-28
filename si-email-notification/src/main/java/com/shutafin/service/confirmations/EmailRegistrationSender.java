package com.shutafin.service.confirmations;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationRegistration;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.ConfirmationRegistrationService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EmailRegistrationSender implements BaseEmailInterface {

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    private ConfirmationRegistrationService confirmationRegistrationService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public void send(EmailNotificationWeb emailNotificationWeb) {

        ConfirmationRegistration confirmationRegistration = confirmationRegistrationService.get(emailNotificationWeb);
        confirmationRegistrationService.save(confirmationRegistration);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, confirmationRegistration.getConfirmationUUID(), CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
