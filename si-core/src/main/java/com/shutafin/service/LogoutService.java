package com.shutafin.service;


import com.shutafin.model.entities.User;

/**
 * Created by Rogov on 24.06.2017.
 */
public interface LogoutService {
    void logout(String sessionId, User user);
}
