package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.service.EmailNotificationSenderService;
import com.shutafin.service.EmailTemplateService;
import com.shutafin.service.InitializationService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@RestController
@RequestMapping("/email")
public class TestController {

    @Autowired
    private EmailNotificationSenderService mailSenderService;

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private InitializationService initializationService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @Transactional
    public void testEmail(@RequestHeader("session_id") String sessioId) {
        User user = sessionManagementService.findUserWithValidSession(sessioId);
        Language language = (Language) initializationService.findAllLanguages().get(1);

        EmailMessage emailMessage = emailTemplateService.getEmailMessage(user, EmailReason.REGISTRATION_CONFIRMATION, language, "http://localhost:7000");
        mailSenderService.sendEmail(emailMessage, EmailReason.REGISTRATION_CONFIRMATION);
    }
}