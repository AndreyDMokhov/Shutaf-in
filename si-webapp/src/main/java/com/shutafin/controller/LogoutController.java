package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.service.LogoutService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/logout")
public class LogoutController {

    private static final int MIN_LENGTH_SESSION_ID = 35;
    @Autowired
    private LogoutService logoutService;


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void logout(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId) || sessionId.length() < MIN_LENGTH_SESSION_ID) {
            throw new AuthenticationException();
        }
        logoutService.logout(sessionId);
    }
}
