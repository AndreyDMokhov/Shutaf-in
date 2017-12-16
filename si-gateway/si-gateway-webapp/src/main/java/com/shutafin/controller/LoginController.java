package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;
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


    //todo ms-gateway @SessionResponse

    @SessionResponse
    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserWeb login(@RequestBody @Valid AccountLoginRequest loginWeb,
                                BindingResult result) {
        log.debug("/login/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return loginWebService.getUserByLoginWebModel(loginWeb);
    }

}
