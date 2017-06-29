package com.shutafin.controller;


import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.model.web.user.UserSettingsWeb;
import com.shutafin.service.UserService;
import com.shutafin.service.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserInfoWeb get(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        System.out.println(sessionId);
        return userService.findByUserId(2L);
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public void save(@RequestBody @Valid UserSettingsWeb userSettingsWeb, HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        System.out.println(sessionId);
//        if (!StringUtils.isBlank(sessionId)) {
//            throw new AuthenticationException();
//        }

        userSettingsService.save(userSettingsWeb, sessionId);

    }

}
