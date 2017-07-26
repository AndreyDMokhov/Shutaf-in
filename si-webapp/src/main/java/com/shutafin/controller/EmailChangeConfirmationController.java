package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.model.web.user.EmailChangedWeb;
import com.shutafin.service.EmailChangeConfirmationService;
import com.shutafin.service.SessionManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by usera on 7/16/2017.
 */

@RestController
@RequestMapping("/api/user/account")
public class EmailChangeConfirmationController {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private EmailChangeConfirmationService emailChangeConfirmationService;

    @RequestMapping(value = "/change-email-request", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void emailChangeRequest (@RequestBody @Valid EmailChangeConfirmationWeb emailChangeConfirmationWeb,
                                    HttpServletRequest request, BindingResult result) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        emailChangeConfirmationService.emailChangeRequest(user, emailChangeConfirmationWeb);
    }

    @RequestMapping(value = "/change-email-confirmation/{link}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public EmailChangedWeb emailChangeConfirmation (@PathVariable("link") String link) {
        EmailChangedWeb emailChangedWeb = emailChangeConfirmationService.emailChangeConfirmation(link);
        return emailChangedWeb;
    }
}
