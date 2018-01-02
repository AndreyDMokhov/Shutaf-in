package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.user.PasswordWeb;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.ResetPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/reset-password")
@NoAuthentication
@Slf4j
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @RequestMapping(value = "/request", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPasswordRequest(@RequestBody @Valid AccountEmailRequest emailWeb, BindingResult result) {
        log.debug("/reset-password/request");
        checkBindingResult(result);
        resetPasswordService.resetPasswordRequest(emailWeb);
    }

    @RequestMapping(value = "/validate/{link}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPasswordValidation(@PathVariable("link") String link) {
        log.debug("/reset-password/validate/{link}");
        if (StringUtils.isBlank(link)){
            log.warn("Link is blank or empty");
            throw new ResourceNotFoundException();
        }
        resetPasswordService.resetPasswordValidation(link);
    }

    @RequestMapping(value = "change/{link}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void passwordChange(@RequestBody @Valid PasswordWeb passwordWeb, BindingResult result, @PathVariable("link") String link) {
        log.debug("/reset-password/change/{link}");
        checkBindingResult(result);
        if (StringUtils.isBlank(link)){
            log.warn("Link is blank or empty");
            throw new ResourceNotFoundException();
        }
        resetPasswordService.passwordChange(passwordWeb, link);
    }

    private static void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }
}
