package com.shutafin.service;

import com.shutafin.model.web.user.UserPersonalInfoWeb;

import java.util.List;

public interface UserService {
    void save(UserPersonalInfoWeb user);
    void update(UserPersonalInfoWeb user);
}
