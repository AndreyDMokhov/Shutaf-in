package com.shutafin.service.sender.email_change;

import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.entity.email_change.ConfirmationEmailChange;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.confirmation.ConfirmationEmailChangeService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.sender.BaseEmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("changeEmail")
public class SenderEmailChangeComponent implements BaseEmailInterface {

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

        ConfirmationEmailChange emailOldObject = confirmationEmailChangeService.get(emailNotificationWeb, null, null);
        ConfirmationEmailChange emailChangeObject = confirmationEmailChangeService.get(emailNotificationWeb, emailNotificationWeb.getEmailChange(), emailOldObject);

        emailOldObject.setConnectedConfirmationEmailChange(emailChangeObject);
        confirmationEmailChangeService.save(emailOldObject);
        confirmationEmailChangeService.save(emailChangeObject);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, emailOldObject.getConfirmationUUID(), emailOldObject.getEmailChange(), CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);

        emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, emailChangeObject.getConfirmationUUID(), emailChangeObject.getEmailChange(), CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
