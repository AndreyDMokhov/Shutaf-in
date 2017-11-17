package com.shutafin.controller;

import com.shutafin.core.service.ChangePasswordService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.ChangePasswordWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @Autowired
    private UserService userService;

    @PutMapping(value = "{userId}/change-password", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changePassword(@RequestBody @Valid ChangePasswordWeb changePasswordWeb,
                               BindingResult result, @PathVariable("userId") Long userId) {
        log.debug("users/password/change");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        changePasswordService.changePassword(changePasswordWeb, userService.findUserById(userId));
    }


}