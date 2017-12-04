package com.shutafin.service;

import com.shutafin.model.web.account.AccountRegistrationRequest;
import com.shutafin.model.web.account.AccountUserWeb;

public interface RegistrationService {

    void save(AccountRegistrationRequest registrationRequestWeb);
    AccountUserWeb confirmRegistration(String link);
}
