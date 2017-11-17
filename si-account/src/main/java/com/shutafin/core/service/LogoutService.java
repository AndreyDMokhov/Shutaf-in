package com.shutafin.core.service;


import com.shutafin.model.entities.User;


public interface LogoutService {
    void logout(String sessionId, User user);
}
