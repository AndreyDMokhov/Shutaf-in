package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationRequestWeb;

public interface RegistrationService {

    void save(RegistrationRequestWeb registrationRequestWeb);
    User confirmRegistration(String link);
}
