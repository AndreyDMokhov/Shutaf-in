package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.processors.annotations.authentication.SessionResponse;
import com.shutafin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@NoAuthentication
//@SessionResponse
@Slf4j
public class RegistrationController {


    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/registration/request", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registration(@RequestBody @Valid RegistrationRequestWeb registrationRequestWeb,
                                       BindingResult result){
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        registrationService.save(registrationRequestWeb);
    }

    @SessionResponse
    @RequestMapping(value = "/registration/confirmation/{link}", method = RequestMethod.GET)
    public void confirmRegistration(@SessionResponse @PathVariable String link/*, HttpServletResponse response*/){
        User user = registrationService.confirmRegistration(link);
        String value = sessionManagementService.generateNewSession(user);

//        response.setHeader("session_id", sessionManagementService.generateNewSession(user));
    }
}
