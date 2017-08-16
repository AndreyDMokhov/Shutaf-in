package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserLanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by evgeny on 6/26/2017.
 */
@RestController
@RequestMapping("/user/account")
@Slf4j
public class UserLanguageController {

    @Autowired
    private UserLanguageService userLanguageService;

    @RequestMapping(value = "/language", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @AuthenticatedUser User user){

        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        userLanguageService.updateUserLanguage(userLanguageWeb, user);
    }

    @RequestMapping(value = "/language", method = RequestMethod.GET)
    public Language get(@AuthenticatedUser User user){
        return userLanguageService.findUserLanguage(user);
    }
}
