package com.shutafin.service;

import com.shutafin.model.web.UserInfoWeb;

import java.util.List;

public interface UserService {
    void save(UserInfoWeb user);
    void update(UserInfoWeb user);
    UserInfoWeb findByUserId(Long userId);
    List<UserInfoWeb> findAll();

    void deleteUser(Long userId);
}
