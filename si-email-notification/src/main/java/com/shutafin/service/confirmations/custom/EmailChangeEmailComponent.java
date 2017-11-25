package com.shutafin.service.confirmations.custom;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.EmailConfirmationService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmations.EmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("changeEmail")
public class EmailChangeEmailComponent implements EmailInterface {

    private static final String CHANGE_EMAIL_CONFIRMATION_URL = "/#/change-email/confirmation/";

    private SenderEmailMessageService senderEmailMessageService;
    private EmailConfirmationService emailConfirmationService;
    private EmailTemplateService emailTemplateService;

    @Autowired
    public EmailChangeEmailComponent(SenderEmailMessageService senderEmailMessageService, EmailConfirmationService emailConfirmationService, EmailTemplateService emailTemplateService) {
        this.senderEmailMessageService = senderEmailMessageService;
        this.emailConfirmationService = emailConfirmationService;
        this.emailTemplateService = emailTemplateService;
    }

    @Override
    public void send(EmailNotificationWeb emailNotificationWeb) {
        EmailMessage emailMessage;

        EmailConfirmation oldEmailObject = emailConfirmationService.get(emailNotificationWeb, null, null);
        EmailConfirmation newEmailObject = emailConfirmationService.get(emailNotificationWeb, emailNotificationWeb.getNewEmail(), oldEmailObject);

        oldEmailObject.setConnectedEmailConfirmation(newEmailObject);
        emailConfirmationService.save(oldEmailObject);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, oldEmailObject, CHANGE_EMAIL_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, newEmailObject, CHANGE_EMAIL_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
