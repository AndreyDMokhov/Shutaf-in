package com.shutafin.service.sender.reset_password;

import com.shutafin.model.entity.reset_password.ConfirmationResetPassword;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.service.confirmation.ConfirmationResetPasswordService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.SenderEmailMessageService;
import com.shutafin.service.sender.BaseEmailInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("resetPassword")
public class SenderResetPasswordComponent implements BaseEmailInterface {

    private static final String RESET_PASSWORD_CONFIRMATION_URL = "/#/reset-password/confirmation/";

    @Autowired
    private SenderEmailMessageService senderEmailMessageService;

    @Autowired
    private ConfirmationResetPasswordService confirmationResetPasswordService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    public void send(EmailNotificationWeb emailNotificationWeb) {

        ConfirmationResetPassword confirmationResetPassword = confirmationResetPasswordService.get(emailNotificationWeb);
        confirmationResetPasswordService.save(confirmationResetPassword);
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, confirmationResetPassword.getConfirmationUUID(), RESET_PASSWORD_CONFIRMATION_URL);
        senderEmailMessageService.sendEmailMessage(emailNotificationWeb, emailMessage);
    }
}
