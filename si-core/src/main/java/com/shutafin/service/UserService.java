package com.shutafin.service;

import com.shutafin.model.web.user.UserInfoWeb;

import java.util.List;

public interface UserService {
    void save(UserInfoWeb user);
    List<UserInfoWeb> findAll();
}

