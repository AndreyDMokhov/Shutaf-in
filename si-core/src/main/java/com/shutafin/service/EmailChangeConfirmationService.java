package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.EmailChangeConfirmationWeb;
import com.shutafin.model.web.user.EmailChangedWeb;

/**
 * Created by usera on 7/16/2017.
 */
public interface EmailChangeConfirmationService {

    void emailChangeRequest(User user, EmailChangeConfirmationWeb emailChangeConfirmationWeb);

    EmailChangedWeb emailChangeConfirmation(String link);
}
