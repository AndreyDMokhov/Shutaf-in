package com.shutafin.service.confirmations.custom;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationNewEmail;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.ConfirmationNewEmailService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmations.EmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("changeEmail")
public class EmailChangeEmailComponent implements EmailInterface {

    private static final String CHANGE_EMAIL_CONFIRMATION_URL = "/#/change-email/confirmation/";

    private SenderEmailMessageService senderEmailMessageService;
    private ConfirmationNewEmailService confirmationNewEmailService;
    private EmailTemplateService emailTemplateService;

    @Autowired
    public EmailChangeEmailComponent(SenderEmailMessageService senderEmailMessageService, ConfirmationNewEmailService confirmationNewEmailService, EmailTemplateService emailTemplateService) {
        this.senderEmailMessageService = senderEmailMessageService;
        this.confirmationNewEmailService = confirmationNewEmailService;
        this.emailTemplateService = emailTemplateService;
    }

    @Override
    public void send(EmailNotificationWeb emailNotificationWeb) {
        EmailMessage emailMessage;

        ConfirmationNewEmail oldEmailObject = confirmationNewEmailService.get(emailNotificationWeb, null, null);
        ConfirmationNewEmail newEmailObject = confirmationNewEmailService.get(emailNotificationWeb, emailNotificationWeb.getNewEmail(), oldEmailObject);

        oldEmailObject.setConnectedConfirmationNewEmail(newEmailObject);
        confirmationNewEmailService.save(oldEmailObject);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, oldEmailObject.getConfirmationUUID(), oldEmailObject.getNewEmail(), CHANGE_EMAIL_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, newEmailObject.getConfirmationUUID(), newEmailObject.getNewEmail(), CHANGE_EMAIL_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
