package com.shutafin.service.validation;

import com.shutafin.model.entity.ConfirmationResetPassword;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.service.ConfirmationResetPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class ValidLinkResetPassword implements BaseValidLinkInterface<ConfirmationResetPassword> {

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
