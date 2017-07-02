package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.model.web.user.UserSettingsWeb;
import com.shutafin.service.UserService;
import com.shutafin.service.UserSettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;



@RestController
@RequestMapping("/userSettings")

public class UserSettingsController {

    @Autowired
    UserSettingsService userSettingsService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserSettingsWeb get(@RequestBody @Valid HttpServletRequest request, BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        String sessionId = request.getHeader("session_id");

        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        UserSettingsWeb userSettingsWeb = userSettingsService.get(sessionId);
        return userSettingsWeb;
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public void save(@RequestBody @Valid UserSettingsWeb userSettingsWeb, HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
//        System.out.println(sessionId);
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        userSettingsService.save(userSettingsWeb, sessionId);
    }

}
