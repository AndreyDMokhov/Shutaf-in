package com.shutafin.controller;

import com.shutafin.model.entities.types.EmailReason;
import com.shutafin.model.smtp.BaseTemplate;
import com.shutafin.model.smtp.EmailMessage;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.EmailNotificationSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    private UserRepository userRepository;


    @RequestMapping(value = "/send", method = RequestMethod.GET)
    @Transactional
    public void testEmail() {
        BaseTemplate baseTemplate = new BaseTemplate(
                "Confirm Registration",
                "http://localhost:7000",
                null,
                "Have a great day, <br>Shutaf-In Team");
        EmailMessage message = new EmailMessage(userRepository.findUserByEmail("edward.kats@outlook.com"), baseTemplate);
        mailSenderService.sendEmail(message, EmailReason.REGISTRATION_CONFIRMATION);
    }
}
