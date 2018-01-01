package com.shutafin.service;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;

public interface RegistrationService {

    void registerUser(AccountRegistrationRequest registrationRequestWeb);
    AccountUserWeb confirmRegistrationUser(String link);
}
