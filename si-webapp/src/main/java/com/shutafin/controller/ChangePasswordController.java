package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.ChangePasswordWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.ChangePasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users/password")
@Slf4j
public class ChangePasswordController {



    @Autowired
    private ChangePasswordService changePasswordService;


    @RequestMapping(value = "change", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changePassword(@RequestBody @Valid ChangePasswordWeb changePasswordWeb,
                               BindingResult result,
                               @AuthenticatedUser User user){

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        changePasswordService.changePassword(changePasswordWeb, user);
    }
}