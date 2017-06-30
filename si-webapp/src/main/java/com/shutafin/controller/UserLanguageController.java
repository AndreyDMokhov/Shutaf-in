package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.service.UserLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by evgeny on 6/26/2017.
 */
@RestController
@RequestMapping("/user/account")
public class UserLanguageController {
    @Autowired
    private UserLanguageService userLanguageService;

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserLanguageWeb userLanguageWeb, @RequestHeader(value = "session_id") String sessionId, BindingResult result, HttpServletResponse response){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userLanguageService.update(userLanguageWeb, sessionId);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Language get(@RequestHeader(value = "session_id") String sessionId){
        return userLanguageService.get(sessionId);
    }
}
