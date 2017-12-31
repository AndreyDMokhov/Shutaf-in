package com.shutafin.service;

import com.shutafin.model.web.account.AccountLoginRequest;
import com.shutafin.model.web.account.AccountUserWeb;

/**
 * Created by Rogov on 22.06.2017.
 */
public interface LoginService {
    AccountUserWeb getUserByLoginWebModel(AccountLoginRequest login);
}
