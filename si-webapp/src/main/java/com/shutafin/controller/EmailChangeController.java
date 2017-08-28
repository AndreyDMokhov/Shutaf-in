package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.model.web.user.EmailChangedResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.EmailChangeConfirmationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/account")
@Slf4j
public class EmailChangeController {

    @Autowired
    private EmailChangeConfirmationService emailChangeConfirmationService;

    @RequestMapping(value = "/change-email-request", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void emailChangeRequest(@RequestBody @Valid EmailChangeConfirmationWeb emailChangeConfirmationWeb,
                                   BindingResult result, @AuthenticatedUser User user) {
        log.debug("/users/account/change-email-request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        emailChangeConfirmationService.emailChangeRequest(user, emailChangeConfirmationWeb);
    }

    @RequestMapping(value = "/change-email-confirmation/{link}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EmailChangedResponse emailChangeConfirmation(@PathVariable("link") String link) {
        log.debug("/users/account/change-email-confirmation/{link}");
        return emailChangeConfirmationService.emailChangeConfirmation(link);
    }
}
