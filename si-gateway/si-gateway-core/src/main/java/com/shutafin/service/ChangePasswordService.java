package com.shutafin.service;

import com.shutafin.model.web.account.AccountChangePasswordWeb;

public interface ChangePasswordService {

    void changePassword(AccountChangePasswordWeb changePasswordWeb, Long userId);
}
