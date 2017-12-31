package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountChangePasswordWeb;

public interface ChangePasswordService {

    void changePassword(AccountChangePasswordWeb changePasswordWeb, User user);
}
