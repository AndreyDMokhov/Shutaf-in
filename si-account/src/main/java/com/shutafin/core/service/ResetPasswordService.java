package com.shutafin.core.service;

import com.shutafin.model.web.account.AccountEmailRequest;
import com.shutafin.model.web.account.AccountResetPassword;

public interface ResetPasswordService {
    void resetPassword(AccountEmailRequest accountEmailRequest);
    void resetPassword(AccountResetPassword accountResetPassword);
}
