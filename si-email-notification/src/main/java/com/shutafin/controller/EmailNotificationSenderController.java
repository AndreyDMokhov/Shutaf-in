package com.shutafin.controller;

import com.shutafin.model.confirmations.EmailNotificationWeb;
import com.shutafin.model.confirmations.EmailChangeResponse;
import com.shutafin.model.confirmations.EmailReason;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.service.EmailService;
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

    private EmailService mailSenderService;

    @Autowired
    public EmailNotificationSenderController(
            EmailService emailService) {
        this.mailSenderService = emailService;
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
    public Object confirmLink(@PathVariable String link, @RequestParam("reason") EmailReason emailReason) {
        return mailSenderService.getConfirmationResponse(link, emailReason);
    }

    @GetMapping(
            value = "/validate/{link}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void isValidLink(@PathVariable String link, @RequestParam("reason") EmailReason emailReason) {
        mailSenderService.getValidLink(link, emailReason);
    }

}
