package com.shutafin.controller;

import com.shutafin.core.service.LoginService;
import com.shutafin.model.entities.User;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.LoginWebModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@Slf4j
public class LoginController {

    private LoginService loginWebService;

    @Autowired
    public LoginController(LoginService loginWebService) {
        this.loginWebService = loginWebService;
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public User login(@RequestBody @Valid LoginWebModel loginWeb, BindingResult result) {
        log.debug("/login/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return loginWebService.getUserByLoginWebModel(loginWeb);
    }

}
