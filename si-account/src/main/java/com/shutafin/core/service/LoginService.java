package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountLoginRequest;


public interface LoginService {
    User getUserByLoginWebModel(AccountLoginRequest login);
}
