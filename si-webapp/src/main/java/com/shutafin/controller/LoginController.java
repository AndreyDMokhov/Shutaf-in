package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.processors.annotations.response.SessionResponse;
import com.shutafin.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@NoAuthentication
@Slf4j
public class LoginController {


    @Autowired
    private LoginService loginWebService;

    @SessionResponse
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User login(@RequestBody @Valid LoginWebModel loginWeb, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return loginWebService.getUserByLoginWebModel(loginWeb);
    }


}
