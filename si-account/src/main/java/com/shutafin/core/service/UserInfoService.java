package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;


public interface UserInfoService {

    void createUserInfo(AccountUserInfoRequest userInfoRequest, User user);
    AccountUserInfoResponseDTO getUserInfo(User user);
    AccountUserInfoResponseDTO getUserInfo(Long userId);
    void updateUserInfo(AccountUserInfoRequest userInfoRequest, User user);

}
