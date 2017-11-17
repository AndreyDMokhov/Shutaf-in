package com.shutafin.controller;

import com.shutafin.core.service.LogoutService;
import com.shutafin.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/logout")
@Slf4j
public class LogoutController {

    private LogoutService logoutService;
    private UserService userService;

    @Autowired
    public LogoutController(
            LogoutService logoutService,
            UserService userService) {
        this.logoutService = logoutService;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void logout(String sessionId,
                       @PathVariable("userId") Long userId) {
        log.debug("/logout/");
        logoutService.logout(sessionId, userService.findUserById(userId));
    }
}
