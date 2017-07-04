package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.ResetPasswordWeb;
import com.shutafin.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class ResetPasswordController {

    @Autowired
    private ResetPasswordService resetPasswordService;

    @RequestMapping(value = "/reset", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void resetPassword(@RequestBody @Valid ResetPasswordWeb resetPasswordWeb, BindingResult result){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        resetPasswordService.resetPassword(resetPasswordWeb);
    }
}
