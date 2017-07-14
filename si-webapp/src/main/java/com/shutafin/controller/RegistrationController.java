package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class RegistrationController {


    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registration(@RequestBody @Valid RegistrationRequestWeb registrationRequestWeb,
                                       BindingResult result, HttpServletResponse response){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        registrationService.save(registrationRequestWeb);
    }

    @RequestMapping(value = "/registration/confirmation/{link}", method = RequestMethod.GET)
    public void setUserAccountConfirmedAndGetSessionId(@PathVariable String link, HttpServletResponse response){
        String sessionId = registrationService.confirmRegistration(link);
        response.setHeader("session_id", sessionId);
    }
}
