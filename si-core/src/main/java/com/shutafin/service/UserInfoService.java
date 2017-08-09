package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.user.UserInfoWeb;


public interface UserInfoService {

    void addUserInfo(UserInfoWeb userInfoWeb, User user);
    UserInfo findUserInfo(User user);
    void updateUserInfo(UserInfoWeb userInfoWeb, User user);

}
