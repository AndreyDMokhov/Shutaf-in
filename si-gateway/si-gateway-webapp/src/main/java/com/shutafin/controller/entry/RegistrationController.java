package com.shutafin.controller.entry;

import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.RegistrationService;
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

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    public RegistrationController() {
    }

    @RequestMapping(value = "/registration/request", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registerUser(@RequestBody @Valid AccountRegistrationRequest registrationRequestWeb,
                             BindingResult result) {
        log.debug("/users/registration/request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        registrationService.registerUser(registrationRequestWeb);
    }

    @RequestMapping(value = "/registration/confirmation/{link}", method = RequestMethod.GET)
    public void confirmRegistrationUser(@PathVariable String link) {
        log.debug("/users/registration/confirmation/{link}");
        if (StringUtils.isBlank(link)) {
            log.warn("Link is blank or empty");
            throw new ResourceNotFoundException();
        }
        registrationService.confirmRegistrationUser(link);
    }
}
