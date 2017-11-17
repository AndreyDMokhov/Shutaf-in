package com.shutafin.controller;

import com.shutafin.core.service.ChangeEmailService;
import com.shutafin.model.entities.User;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.EmailChangeWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/account")
@Slf4j
public class EmailChangeController {

    @Autowired
    private ChangeEmailService changeEmailService;

    @RequestMapping(value = "/change-email", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public boolean emailChange(@RequestBody @Valid EmailChangeWeb emailChangeWeb,
                                    BindingResult result,
                                    @AuthenticatedUser User user) {
        log.debug("/users/account/change-email-request");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        return changeEmailService.changeEmail(user, emailChangeWeb);
    }

}
