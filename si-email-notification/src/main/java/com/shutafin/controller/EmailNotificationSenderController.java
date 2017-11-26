package com.shutafin.controller;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailConfirmationResponse;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.service.EmailNotificationService;
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

    private EmailNotificationService mailSenderService;

    @Autowired
    public EmailNotificationSenderController(
            EmailNotificationService emailNotificationService) {
        this.mailSenderService = emailNotificationService;
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
    public EmailConfirmationResponse confirmLink(@PathVariable String link) {
        return mailSenderService.getUserIdFromConfirmation(link);
    }

    @GetMapping(
            value = "/validate/{link}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void isValidLink(@PathVariable String link, @RequestParam("ctype") Integer confirmationType) {
        mailSenderService.getValidLink(link);
    }

}
