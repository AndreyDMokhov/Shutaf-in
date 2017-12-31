package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserWeb;

public interface UserService {
    void save(AccountUserInfoRequest user);
    User findUserById(Long userId);
    AccountUserWeb getAccountUserWebById(Long userId);
}
