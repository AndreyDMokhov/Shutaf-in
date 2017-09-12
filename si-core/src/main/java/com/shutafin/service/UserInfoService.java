package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.model.web.user.UserInfoWeb;


public interface UserInfoService {

    void createUserInfo(UserInfoWeb userInfoWeb, User user);
    UserInfoResponse getUserInfo(User user);
    void updateUserInfo(UserInfoWeb userInfoWeb, User user);

}
