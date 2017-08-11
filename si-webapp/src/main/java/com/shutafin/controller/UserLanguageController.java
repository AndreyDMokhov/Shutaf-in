package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserLanguageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by evgeny on 6/26/2017.
 */
@RestController
@RequestMapping("/user/account")
public class UserLanguageController {
    @Autowired
    private UserLanguageService userLanguageService;
    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/language", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserLanguageWeb userLanguageWeb, BindingResult result, @RequestHeader(value = "session_id") String sessionId) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        userLanguageService.updateUserLanguage(userLanguageWeb, sessionManagementService.findUserWithValidSession(sessionId));
    }

    @RequestMapping(value = "/language", method = RequestMethod.GET)
    public Language get(@RequestHeader(value = "session_id") String sessionId) {
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        return userLanguageService.findUserLanguage(sessionManagementService.findUserWithValidSession(sessionId));
    }
}
