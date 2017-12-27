package com.shutafin.controller;

import com.shutafin.core.service.ChangeEmailService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.email.EmailNotificationWeb;
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
    public EmailNotificationWeb validateChangeEmailRequest(@RequestBody @Valid AccountEmailChangeValidationRequest emailChangeWeb,
                                                           BindingResult result,
                                                           @PathVariable("userId") Long userId) {
        log.debug("/users/account/change-email-request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        User user = userService.findUserById(userId);
        return changeEmailService.changeEmailChangeValidationRequest(user, emailChangeWeb);
    }

    @PutMapping(value = "/{userId}/change-email")
    public void changeEmail(@PathVariable("userId") Long userId,
                            @Valid @RequestBody AccountEmailChangeRequest accountEmailChangeRequest,
                            BindingResult result) {
        log.debug("/users/account/change-email-request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        changeEmailService.changeEmailChangeRequest(userService.findUserById(userId), accountEmailChangeRequest);
    }

}
