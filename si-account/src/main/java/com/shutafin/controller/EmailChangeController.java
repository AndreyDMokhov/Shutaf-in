package com.shutafin.controller;

import com.shutafin.core.service.ChangeEmailService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountEmailChangeWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class EmailChangeController {

    @Autowired
    private ChangeEmailService changeEmailService;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/{userId}/change-email", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void emailChange(@RequestBody @Valid AccountEmailChangeWeb emailChangeWeb,
                               BindingResult result,
                               @PathVariable("userId") Long userId) {
        log.debug("/users/account/change-email-request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        changeEmailService.changeEmail(userService.findUserById(userId), emailChangeWeb);
    }

}
