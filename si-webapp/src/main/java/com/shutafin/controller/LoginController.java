package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {


    @Autowired
    private LoginService loginWebService;


    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void login(@RequestBody @Valid LoginWebModel loginWeb, BindingResult result, HttpServletResponse response) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        String session = loginWebService.getSessionIdByEmail(loginWeb);
        response.addHeader("session_id", session);
    }


}
