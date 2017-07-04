package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.service.UserAccountSettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/usersettings")

public class UserAccountSettingsController {

    @Autowired
    UserAccountSettingsService userAccountSettingsService;


    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserAccountSettingsWeb get(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        UserAccountSettingsWeb userAccountSettingsWeb = userAccountSettingsService.get(sessionId);
        return userAccountSettingsWeb;
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody @Valid UserAccountSettingsWeb userAccountSettingsWeb, HttpServletRequest request, BindingResult result) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userAccountSettingsService.save(userAccountSettingsWeb, sessionId);
    }

}
