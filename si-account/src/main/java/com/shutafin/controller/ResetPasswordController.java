package com.shutafin.controller;

import com.shutafin.core.service.ResetPasswordService;
import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;
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
    public EmailNotificationWeb getResetPasswordEmailNotification(@RequestBody AccountEmailRequest accountEmailRequest,
                                                                  BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        return resetPasswordService.getResetPasswordEmailNotification(accountEmailRequest);
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
