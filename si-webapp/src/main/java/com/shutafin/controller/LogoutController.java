package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.AuthenticatedUserType;
import com.shutafin.service.LogoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logout")
public class LogoutController {

    @Autowired
    private LogoutService logoutService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void logout(@AuthenticatedUser(value = AuthenticatedUserType.SESSION_ID) String sessionId,
                       @AuthenticatedUser(value = AuthenticatedUserType.USER) User user) {

        logoutService.logout(sessionId, user);
    }
}
