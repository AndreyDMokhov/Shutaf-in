package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.model.web.user.UserInfoResponseDTO;


public interface UserInfoService {

    void createUserInfo(UserInfoRequest userInfoRequest, User user);
    UserInfoResponseDTO getUserInfo(User user);
    UserInfoResponseDTO getUserInfo(Long userId);
    void updateUserInfo(UserInfoRequest userInfoRequest, User user);

}
