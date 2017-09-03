package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.LoginWebModel;

/**
 * Created by Rogov on 22.06.2017.
 */
public interface LoginService {
    User getUserByEmail(LoginWebModel login);
}
