package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.RegistrationConfirmationWeb;

/**
 * Created by evgeny on 7/10/2017.
 */
public interface RegistrationConfirmationService {
    public Boolean isUserConfirmed(User user);
}
