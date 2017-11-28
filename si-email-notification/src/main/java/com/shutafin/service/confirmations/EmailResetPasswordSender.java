package com.shutafin.service.confirmations;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.entity.ConfirmationResetPassword;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.ConfirmationResetPasswordService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class EmailResetPasswordSender implements BaseEmailInterface {

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    private ConfirmationResetPasswordService confirmationResetPasswordService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public void send(EmailNotificationWeb emailNotificationWeb) {

        ConfirmationResetPassword confirmationResetPassword = confirmationResetPasswordService.get(emailNotificationWeb);
        confirmationResetPasswordService.save(confirmationResetPassword);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, confirmationResetPassword.getConfirmationUUID(), CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
