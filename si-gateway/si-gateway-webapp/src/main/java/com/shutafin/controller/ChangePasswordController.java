package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountChangePasswordWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.ChangePasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/password")
@Slf4j
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @RequestMapping(value = "/change", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changePassword(@RequestBody @Valid AccountChangePasswordWeb changePasswordWeb,
                               BindingResult result,
                               @AuthenticatedUser Long userId) {
        log.debug("users/password/change");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        changePasswordService.changePassword(changePasswordWeb, userId);
    }
}