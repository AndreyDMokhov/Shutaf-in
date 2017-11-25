package com.shutafin.service.confirmations;

import org.springframework.stereotype.Component;

@Component("registration")
public class EmailRegistrationComponent extends BaseEmailSender {

    private static final String REGISTRATION_CONFIRMATION_URL = "/#/registration/confirmation/";

    public EmailRegistrationComponent() {
        super(REGISTRATION_CONFIRMATION_URL);
    }
}
