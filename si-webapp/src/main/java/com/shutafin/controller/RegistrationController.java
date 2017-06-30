package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class RegistrationController {

    private static final String STRING_SESSION_D = "session_id";

    @Autowired
    private RegistrationService registrationService;

    private String sessionId;

    @ModelAttribute
    public void setResponseHeader(HttpServletResponse response) {
        response.setHeader(STRING_SESSION_D, sessionId);
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registration(@RequestBody @Valid RegistrationRequestWeb registrationRequestWeb,
                                       BindingResult result, HttpServletResponse response){
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        sessionId = registrationService.save(registrationRequestWeb);
        setResponseHeader(response);
    }
}