package com.shutafin.controller;

import com.shutafin.core.service.ResetPasswordService;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/reset-password")
@Slf4j
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;


    @PostMapping("/request")
    public void resetPasswordRequest(@RequestBody AccountEmailRequest accountEmailRequest, BindingResult result) {
        log.debug("/users/reset-password/request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        resetPasswordService.resetPasswordRequest(accountEmailRequest);
    }

    @PutMapping("/confirmation")
    public void resetPassword(@RequestBody @Valid AccountResetPassword accountResetPassword,
                              BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        resetPasswordService.resetPassword(accountResetPassword);
    }
}
