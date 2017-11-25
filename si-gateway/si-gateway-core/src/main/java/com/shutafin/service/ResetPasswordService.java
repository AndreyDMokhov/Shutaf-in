package com.shutafin.service;

import com.shutafin.model.web.user.EmailWeb;
import com.shutafin.model.web.user.PasswordWeb;

public interface ResetPasswordService {

    void resetPasswordRequest(EmailWeb emailWeb);
    void resetPasswordValidation(String link);
    void passwordChange(PasswordWeb passwordWeb, String link);
}
