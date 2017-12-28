package com.shutafin.core.service;

import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;


public interface LoginService {
    AccountUserWeb getUserByLoginWebModel(AccountLoginRequest login);
}
