package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.model.web.user.UserInfoRequest;


public interface UserInfoService {

    void createUserInfo(UserInfoRequest userInfoRequest, User user);
    UserInfoResponse getUserInfo(User user);
    void updateUserInfo(UserInfoRequest userInfoRequest, User user);

}
