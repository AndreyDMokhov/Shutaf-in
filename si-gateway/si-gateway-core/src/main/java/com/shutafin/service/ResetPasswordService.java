package com.shutafin.service;

import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.user.PasswordWeb;

public interface ResetPasswordService {

    void resetPasswordRequest(AccountEmailRequest emailWeb);
    void resetPasswordValidation(String link);
    void passwordChange(PasswordWeb passwordWeb, String link);
}
