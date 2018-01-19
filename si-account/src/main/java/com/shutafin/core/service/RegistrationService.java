package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountRegistrationRequest;

public interface RegistrationService {

    void registerUser(AccountRegistrationRequest registrationRequestWeb);

    User confirmRegistration(Long userId);
}
