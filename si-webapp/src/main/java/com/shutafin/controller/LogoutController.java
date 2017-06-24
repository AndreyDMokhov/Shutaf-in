package com.shutafin.controller;

import com.shutafin.service.LogoutService;
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


    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void logout(HttpServletRequest request) {
        logoutService.logout(request.getHeader("session_id"));
    }
}
