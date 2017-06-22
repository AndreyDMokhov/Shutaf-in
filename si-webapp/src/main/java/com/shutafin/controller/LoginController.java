package com.shutafin.controller;

import com.shutafin.exception.exceptions.InputValidationException;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.service.impl.LoginWebServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {



    @Autowired
    private LoginWebServiceImpl loginWebService;


    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void login(@RequestBody @Valid LoginWebModel loginWeb, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        String sessionId = loginWebService.login(loginWeb);
        response.addHeader("session_id", sessionId);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void logout(@RequestBody @Valid HttpServletRequest request) {
        System.out.println(request.toString());
        loginWebService.logout(request.toString());
    }
}
