package com.shutafin.controller;

import com.shutafin.core.service.LoginService;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class LoginController {

    private LoginService loginWebService;

    @Autowired
    public LoginController(LoginService loginWebService) {
        this.loginWebService = loginWebService;
    }

    @PostMapping(value = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserWeb login(@RequestBody @Valid AccountLoginRequest loginWeb, BindingResult result) {
        log.debug("/login/");
        checkResult(result);
        return loginWebService.getUserByLoginWebModel(loginWeb);
    }

    @PostMapping(value = "/resend/registration", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void resendEmailRegistration(@RequestBody @Valid AccountLoginRequest loginWeb, BindingResult result) {
        log.debug("/users/resend/registration");
        checkResult(result);
        loginWebService.resendEmailRegistration(loginWeb);
    }

    private void checkResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

}
