package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.LoginWebModel;


public interface LoginService {
    User getUserByLoginWebModel(LoginWebModel login);
}
