package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountEmailChangeRequest;
import com.shutafin.model.web.account.AccountEmailChangeValidationRequest;

public interface ChangeEmailService {
    void changeEmailChangeValidationRequest(User user, AccountEmailChangeValidationRequest emailChangeWeb);
    void changeEmailChangeRequest(User user, AccountEmailChangeRequest emailChangeWeb);
}
