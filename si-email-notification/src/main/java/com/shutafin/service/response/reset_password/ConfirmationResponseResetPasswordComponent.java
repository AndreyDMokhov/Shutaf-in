package com.shutafin.service.response.reset_password;

import com.shutafin.model.entity.confirmation.reset_password.ConfirmationResetPassword;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.web.email.response.EmailResetPasswordResponse;
import com.shutafin.service.confirmation.ConfirmationResetPasswordService;
import com.shutafin.service.response.BaseConfirmationResponseInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("resetPasswordConfirmationResponse")
@Slf4j
public class ConfirmationResponseResetPasswordComponent implements BaseConfirmationResponseInterface<EmailResetPasswordResponse> {

    @Autowired
    private ConfirmationResetPasswordService confirmationResetPasswordService;

    @Override
    public EmailResetPasswordResponse getResponse(String link) {

        ConfirmationResetPassword confirmationResetPassword = confirmationResetPasswordService.getConfirmed(link);
        if (confirmationResetPassword == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        confirmationResetPassword.setIsConfirmed(true);
        confirmationResetPasswordService.save(confirmationResetPassword);

        EmailResetPasswordResponse emailResetPasswordResponse = new EmailResetPasswordResponse();
        emailResetPasswordResponse.setUserId(confirmationResetPassword.getUserId());
        return emailResetPasswordResponse;
    }

    @Override
    public void revertConfirmation(String link) {
        confirmationResetPasswordService.revertConfirmation(link);
    }
}
