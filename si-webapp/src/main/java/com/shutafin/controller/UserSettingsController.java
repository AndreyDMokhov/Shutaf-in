package com.shutafin.controller;


import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.model.web.user.UserSettingsWeb;
import com.shutafin.service.UserService;
import com.shutafin.service.UserSettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/userSettings")

public class UserSettingsController {

    @Autowired
    UserSettingsService userSettingsService;

//    only test
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public UserInfoWeb get(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
//        only test
        System.out.println(sessionId);
        List<UserInfoWeb> users = userService.findAll();
        return users.get(1);
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
