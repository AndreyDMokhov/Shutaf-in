package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.UserSession;
import com.shutafin.service.LogoutService;
import com.shutafin.service.SessionManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/logout")
public class LogoutController {


    @Autowired
    private LogoutService logoutService;

    @Autowired
    private
    SessionManagementService sessionManagementService;


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void logout(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }

        UserSession userSession = sessionManagementService.findValidUserSession(sessionId);
        if (userSession == null) {
            throw new AuthenticationException();
        }
        logoutService.logout(userSession);
    }
}
