package com.shutafin.core.service;

import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;
import com.shutafin.model.web.email.EmailNotificationWeb;

public interface ResetPasswordService {
    EmailNotificationWeb getResetPasswordEmailNotification(AccountEmailRequest accountEmailRequest);
    void resetPassword(AccountResetPassword accountResetPassword);
}
