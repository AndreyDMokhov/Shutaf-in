package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountEmailChangeWeb;

public interface ChangeEmailService {
    void changeEmail(User user, AccountEmailChangeWeb emailChangeWeb);
}
