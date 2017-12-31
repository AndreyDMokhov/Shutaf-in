package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.user.GatewayEmailChangedResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.EmailChangeConfirmationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public void emailChangeRequest (@RequestBody @Valid AccountEmailChangeValidationRequest emailChangeConfirmationWeb,
                                    BindingResult result,
                                    @AuthenticatedUser Long userId) {
        log.debug("/users/account/change-email-request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        emailChangeConfirmationService.emailChangeRequest(userId, emailChangeConfirmationWeb);
    }

    @NoAuthentication
    @RequestMapping(value = "/change-email-confirmation/{link}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public GatewayEmailChangedResponse emailChangeConfirmation (@PathVariable("link") String link) {
        log.debug("/users/account/change-email-confirmation/{link}");
        if (StringUtils.isBlank(link)){
            log.warn("Link is blank or empty");
            throw new ResourceNotFoundException();
        }
        return emailChangeConfirmationService.emailChangeConfirmation(link);
    }
}
