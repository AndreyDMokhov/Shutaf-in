package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.email.EmailNotificationWeb;
import com.shutafin.model.web.email.response.EmailRegistrationResponse;
import com.shutafin.sender.account.RegistrationControllerSender;
import com.shutafin.sender.email.EmailNotificationSenderControllerSender;
import com.shutafin.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationControllerSender registrationControllerSender;

    @Autowired
    private EmailNotificationSenderControllerSender emailNotificationSenderControllerSender;


    @Override
    public void registerUser(AccountRegistrationRequest registrationRequestWeb) {

        EmailNotificationWeb emailNotificationWeb = registrationControllerSender.registerUser(registrationRequestWeb);

        emailNotificationSenderControllerSender.sendEmail(emailNotificationWeb);
    }

    @Override
    public AccountUserWeb confirmRegistrationUser(String link) {
        EmailRegistrationResponse emailRegistrationResponse = emailNotificationSenderControllerSender.confirmLink(link);

        return registrationControllerSender.confirmRegistration(emailRegistrationResponse.getUserId());
    }

}