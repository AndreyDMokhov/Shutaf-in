package com.shutafin.service;

import com.shutafin.model.web.user.RegistrationRequestWeb;

public interface RegistrationService {

    void save(RegistrationRequestWeb registrationRequestWeb);
    String confirmRegistration(String link);
}
