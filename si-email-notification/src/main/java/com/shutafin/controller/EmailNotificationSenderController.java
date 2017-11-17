package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.model.entity.EmailReason;
import com.shutafin.model.web.EmailNotificationWeb;
import com.shutafin.service.EmailNotificationSenderService;
import com.shutafin.service.EmailTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/email")
@Slf4j
public class EmailNotificationSenderController {

    private EmailNotificationSenderService mailSenderService;

    private EmailTemplateService emailTemplateService;

    @Autowired
    public EmailNotificationSenderController(
            EmailNotificationSenderService emailNotificationSenderService,
            EmailTemplateService emailTemplateService) {
        this.mailSenderService = emailNotificationSenderService;
        this.emailTemplateService = emailTemplateService;
    }

    @PostMapping(
            value = "/registration",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void registration(@RequestBody @Valid EmailNotificationWeb emailNotificationWeb, BindingResult result) {
        checkBindingResult(result);
        sendEmail(emailNotificationWeb, EmailReason.REGISTRATION_CONFIRMATION);
    }

    @PostMapping(
            value = "/change/email",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void changeEmail(@RequestBody @Valid EmailNotificationWeb emailNotificationWeb, BindingResult result) {
        checkBindingResult(result);
        sendEmail(emailNotificationWeb, EmailReason.CHANGE_EMAIL);
    }

    @PostMapping(
            value = "/change/password",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void changePassword(@RequestBody @Valid EmailNotificationWeb emailNotificationWeb, BindingResult result) {
        checkBindingResult(result);
        sendEmail(emailNotificationWeb, EmailReason.CHANGE_PASSWORD);
    }

    @PostMapping(
            value = "/reset/password",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public void resetPassword(@RequestBody @Valid EmailNotificationWeb emailNotificationWeb, BindingResult result) {
        checkBindingResult(result);
        sendEmail(emailNotificationWeb, EmailReason.RESET_PASSWORD);
    }

    private void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

    private void sendEmail(EmailNotificationWeb emailNotificationWeb, EmailReason registrationConfirmation) {
        EmailMessage emailMessage = emailTemplateService.getEmailMessage(emailNotificationWeb, registrationConfirmation);
        mailSenderService.sendEmail(emailMessage, registrationConfirmation);
    }

}
