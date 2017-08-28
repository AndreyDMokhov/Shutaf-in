package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.processors.annotations.sessionResponse.SessionResponse;
import com.shutafin.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/registration")
@NoAuthentication
@Slf4j
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/request", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registration(@RequestBody @Valid RegistrationRequestWeb registrationRequestWeb,
                             BindingResult result) {
        log.debug("/users/registration/request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        registrationService.save(registrationRequestWeb);
    }

    @SessionResponse
    @RequestMapping(value = "/confirmation/{link}", method = RequestMethod.GET)
    public User confirmRegistration(@PathVariable String link) {
        log.debug("/users/registration/confirmation/{link}");
        return registrationService.confirmRegistration(link);
    }

}
