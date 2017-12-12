package com.shutafin.service.validation.password.reset;

import com.shutafin.model.entity.reset_password.ConfirmationResetPassword;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.confirmation.ConfirmationResetPasswordService;
import com.shutafin.service.validation.BaseValidationLinkInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("resetPasswordValidationLink")
@Slf4j
public class ValidationLinkResetPasswordComponent implements BaseValidationLinkInterface<ConfirmationResetPassword> {

    @Autowired
    private ConfirmationResetPasswordService confirmationResetPasswordService;

    @Override
    public ConfirmationResetPassword validate(String link) {

        ConfirmationResetPassword confirmationResetPassword = confirmationResetPasswordService.getConfirmed(link);
        if (confirmationResetPassword == null) {
            log.warn("Resource not found exception:");
            log.warn("UrlLink {} was not found", link);
            throw new ResourceNotFoundException();
        }
        return confirmationResetPassword;
    }

}
