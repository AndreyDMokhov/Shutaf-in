package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.service.SessionManagementService;
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

    @Autowired
    SessionManagementService sessionManagementService;

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserAccountSettingsWeb getCurrentAccountSettingsWeb(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        UserAccountSettingsWeb userAccountSettingsWeb = userAccountSettingsService.getCurrentAccountSettings(user);
        return userAccountSettingsWeb;
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void saveNewAccountSettingsWeb(@RequestBody @Valid UserAccountSettingsWeb userAccountSettingsWeb, HttpServletRequest request, BindingResult result) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        userAccountSettingsService.saveNewAccountSettings(userAccountSettingsWeb, user);
    }

}
