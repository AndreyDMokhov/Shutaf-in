package com.shutafin.controller;


import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.user.UserSettingsWeb;
import com.shutafin.service.UserSettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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


    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserSettingsWeb get(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
//        only test
        System.out.println(sessionId);
        UserSettingsWeb userSettingsWeb = new UserSettingsWeb("first", "last", "ru");
        System.out.println("From Server: "+userSettingsWeb.getFirstName()+" "+userSettingsWeb.getLastName()+" "+userSettingsWeb.getLanguageId());
        return userSettingsWeb;
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody @Valid UserSettingsWeb userSettingsWeb, HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        System.out.println(sessionId);
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }

        System.out.println("To Server: "+userSettingsWeb.getFirstName()+" "+userSettingsWeb.getLastName()+" "+userSettingsWeb.getLanguageId());
//        userSettingsService.save(userSettingsWeb, sessionId);

    }

}
