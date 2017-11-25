package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoRequest;

public interface UserService {
    void save(UserInfoRequest user);
    User findUserById(Long userId);
}
