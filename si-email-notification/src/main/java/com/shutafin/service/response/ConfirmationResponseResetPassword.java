package com.shutafin.service.response;

import com.shutafin.model.confirmations.ResetPasswordResponse;
import com.shutafin.model.entity.ConfirmationResetPassword;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.ConfirmationResetPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ConfirmationResponseResetPassword implements BaseConfirmationResponseInterface<ResetPasswordResponse> {

    @Autowired
    private ConfirmationResetPasswordService confirmationResetPasswordService;

    @Override
    public ResetPasswordResponse getResponse(String link) {

        ConfirmationResetPassword confirmationResetPassword = confirmationResetPasswordService.getConfirmed(link);
        if (confirmationResetPassword == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }

        confirmationResetPassword.setIsConfirmed(true);
        confirmationResetPasswordService.save(confirmationResetPassword);

        ResetPasswordResponse resetPasswordResponse = new ResetPasswordResponse();
        resetPasswordResponse.setUserId(confirmationResetPassword.getUserId());
        return resetPasswordResponse;
    }
}
