package com.shutafin.controller;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.*;
import org.apache.commons.lang3.StringUtils;
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
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/registration/request", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registration(@RequestBody @Valid RegistrationRequestWeb registrationRequestWeb,
                                       BindingResult result){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        registrationService.save(registrationRequestWeb);
    }

    @RequestMapping(value = "/registration/confirmation/{link}", method = RequestMethod.GET)
    public void confirmRegistration(@PathVariable String link, HttpServletResponse response){
        if (StringUtils.isBlank(link)){
            throw new ResourceNotFoundException();
        }
        User user = registrationService.confirmRegistration(link);
        response.setHeader("session_id", sessionManagementService.generateNewSession(user));
    }
}
