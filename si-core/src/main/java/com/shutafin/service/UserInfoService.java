package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.model.web.user.UserInfoRequest;


public interface UserInfoService {

    void createUserInfo(UserInfoRequest userInfoRequest, User user);
    UserInfoResponseDTO getUserInfo(User user);
    UserInfoResponseDTO getUserInfo(Long userId);
    void updateUserInfo(UserInfoRequest userInfoRequest, User user);

}
