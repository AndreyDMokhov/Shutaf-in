package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userSettings")

public class UserSettingsController {


    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public void logout(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        System.out.println(sessionId);
        if (!StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
    }

}
