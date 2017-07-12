package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.EmailWeb;
import com.shutafin.model.web.user.PasswordWeb;
import com.shutafin.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("reset-password")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @RequestMapping(value = "request", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPasswordRequest(@RequestBody @Valid EmailWeb emailWeb, BindingResult result){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        resetPasswordService.resetPasswordRequest(emailWeb);
    }

    @RequestMapping(value = "validate/{link}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPasswordValidation(@PathVariable("link") String link){
        resetPasswordService.resetPasswordValidation(link);
    }

    @RequestMapping(value = "change/{link}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void passwordChange(@RequestBody @Valid PasswordWeb passwordWeb, BindingResult result, @PathVariable("link") String link){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        resetPasswordService.passwordChange(passwordWeb, link);
    }
}
