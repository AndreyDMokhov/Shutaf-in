package com.shutafin.service.confirmations;

import org.springframework.stereotype.Component;

@Component("resetPassword")
public class EmailResetPasswordComponent extends BaseEmailSender {

    private static final String RESET_PASSWORD_CONFIRMATION_URL = "/#/reset-password/confirmation/";

    public EmailResetPasswordComponent() {
        super(RESET_PASSWORD_CONFIRMATION_URL);
    }
}
