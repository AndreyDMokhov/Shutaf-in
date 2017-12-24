package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.EmailReason;
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
    public void sendEmail(@RequestBody @Valid EmailNotificationWeb emailNotificationWeb, BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        mailSenderService.sendEmail(emailNotificationWeb);
    }

    @GetMapping("/confirm")
    public Object confirmLink(@RequestParam("link") String link, @RequestParam("reason") EmailReason emailReason) {
        return mailSenderService.getConfirmationResponse(link, emailReason);
    }

    @GetMapping(
            value = "/validate/{link}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Boolean isValidLink(@PathVariable String link, @RequestParam("reason") EmailReason emailReason) {
        return mailSenderService.getValidLink(link, emailReason) != null;
    }

}
