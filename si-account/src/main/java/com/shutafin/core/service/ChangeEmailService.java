package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;
import com.shutafin.model.web.email.EmailNotificationWeb;

public interface ChangeEmailService {
    EmailNotificationWeb changeEmailChangeValidationRequest(User user, AccountEmailChangeValidationRequest emailChangeWeb);
    void changeEmailChangeRequest(User user, AccountEmailChangeRequest emailChangeWeb);
}
