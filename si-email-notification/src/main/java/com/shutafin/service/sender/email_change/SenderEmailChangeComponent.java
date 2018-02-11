package com.shutafin.service.sender.email_change;

import com.shutafin.model.entity.confirmation.email_change.ConfirmationEmailChange;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.confirmation.ConfirmationEmailChangeService;
import com.shutafin.service.sender.BaseEmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("emailChange")
public class SenderEmailChangeComponent implements BaseEmailInterface {

    private static final String EMAIL_CHANGE_CONFIRMATION_URL = "/#/settings/change-email/confirmation/";

    private SenderEmailMessageService senderEmailMessageService;
    private ConfirmationEmailChangeService confirmationEmailChangeService;
    private EmailTemplateService emailTemplateService;

    @Autowired
    public SenderEmailChangeComponent(SenderEmailMessageService senderEmailMessageService, ConfirmationEmailChangeService confirmationEmailChangeService, EmailTemplateService emailTemplateService) {
        this.senderEmailMessageService = senderEmailMessageService;
        this.confirmationEmailChangeService = confirmationEmailChangeService;
        this.emailTemplateService = emailTemplateService;
    }

    @Override
    public void send(EmailNotificationWeb emailNotificationWeb) {
        EmailMessage emailMessage;

        ConfirmationEmailChange fromEmail = confirmationEmailChangeService.get(
                emailNotificationWeb,
                null,
                null);

        ConfirmationEmailChange toEmail = confirmationEmailChangeService.get(
                emailNotificationWeb,
                emailNotificationWeb.getEmailChange(),
                fromEmail);

        fromEmail.setConnectedConfirmationEmailChange(toEmail);
        toEmail.setConnectedConfirmationEmailChange(fromEmail);

        confirmationEmailChangeService.save(fromEmail);
        confirmationEmailChangeService.save(toEmail);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, fromEmail.getConfirmationUUID(), fromEmail.getEmailChange(), EMAIL_CHANGE_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb.getEmailReason(), emailMessage);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, toEmail.getConfirmationUUID(), toEmail.getEmailChange(), EMAIL_CHANGE_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb.getEmailReason(), emailMessage);
    }
}
