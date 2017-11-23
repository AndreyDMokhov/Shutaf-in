package com.shutafin.controller;

import com.shutafin.model.email.EmailNotificationWeb;
import com.shutafin.model.email.EmailResponse;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.service.EmailNotificationSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailNotificationSenderController {

    private EmailNotificationSenderService mailSenderService;

    @Autowired
    public EmailNotificationSenderController(
            EmailNotificationSenderService emailNotificationSenderService) {
        this.mailSenderService = emailNotificationSenderService;
    }

    @PostMapping(
            value = "/send",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void registration(@RequestBody @Valid EmailNotificationWeb emailNotificationWeb, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        mailSenderService.sendEmail(emailNotificationWeb);
    }

    @GetMapping(
            value = "/confirm/{link}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public EmailResponse confirmLink(@PathVariable String link) {
        return mailSenderService.getUserIdFromConfirmation(link);
    }

    @GetMapping(
            value = "/validate/{link}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void isValidateLink(@PathVariable String link) {
        mailSenderService.isValidLink(link);
    }

}
