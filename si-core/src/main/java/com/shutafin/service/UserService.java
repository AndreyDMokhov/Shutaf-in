package com.shutafin.service;

import com.shutafin.model.web.user.UserInfoWeb;

@Deprecated
public interface UserService {
    void save(UserInfoWeb user);
    void update(UserInfoWeb user);
}
