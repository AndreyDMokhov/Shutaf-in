package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;

/**
 * Created by usera on 7/16/2017.
 */
public interface EmailChangeConfirmationService {

    void emailChangeRequest(User user, EmailChangeConfirmationWeb emailChangeConfirmationWeb);

    void emailChangeConfirmation (User user, EmailChangeConfirmationWeb EmailChangeConfirmationWeb);
}
