package com.shutafin.controller;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.processors.annotations.response.SessionResponse;
import com.shutafin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@NoAuthentication
@Slf4j
public class RegistrationController {


    @Autowired
    private RegistrationService registrationService;


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
    public User confirmRegistration(@PathVariable String link){
        if (StringUtils.isBlank(link)){
            throw new ResourceNotFoundException();
        }
        return registrationService.confirmRegistration(link);
    }
}
