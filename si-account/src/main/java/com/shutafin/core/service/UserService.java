package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserWeb;

import java.util.List;

public interface UserService {
    void save(AccountUserInfoRequest user);
    User findUserById(Long userId);
    List<User> findUsersByIds(List<Long> userIds);
    AccountUserWeb getAccountUserWebById(Long userId);
}
