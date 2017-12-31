package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.email.EmailNotificationWeb;

public interface RegistrationService {

    EmailNotificationWeb registerUser(AccountRegistrationRequest registrationRequestWeb);

    User confirmRegistration(Long userId);
}
