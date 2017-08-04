package com.shutafin.service;

import com.shutafin.model.entities.UserSession;

/**
 * Created by Rogov on 24.06.2017.
 */
public interface LogoutService {
    void logout(UserSession userSession);
}
