package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.user.UserInfoRequest;


public interface UserInfoService {

    void createUserInfo(UserInfoRequest userInfoRequest, User user);
    AccountUserInfoResponseDTO getUserInfo(Long userId);
    void updateUserInfo(UserInfoRequest userInfoRequest, User user);

}
