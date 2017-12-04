package com.shutafin.controller;

import com.shutafin.core.service.RegistrationService;
import com.shutafin.model.entities.User;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class RegistrationController {

    private RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping(value = "/registration/request", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void registration(@RequestBody @Valid AccountRegistrationRequest registrationRequestWeb,
                             BindingResult result) {
        log.debug("/users/registration/request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        registrationService.save(registrationRequestWeb);
    }

    @GetMapping(value = "/registration/confirm/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserWeb confirmRegistration(@PathVariable Long userId) {
        log.debug("/users/registration/confirmation/{userId}");
        User user = registrationService.confirmRegistration(userId);

        return AccountUserWeb
                .builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .id(user.getId())
                .build();
    }

}
