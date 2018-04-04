package com.shutafin.service;

import com.shutafin.model.web.account.AccountRegistrationRequest;

public interface RegistrationService {

    void registerUser(AccountRegistrationRequest registrationRequestWeb);
    void confirmRegistrationUser(String link);
}
