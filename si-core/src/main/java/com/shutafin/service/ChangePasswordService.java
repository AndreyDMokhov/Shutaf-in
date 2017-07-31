package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.ChangePasswordWeb;

public interface ChangePasswordService {

    void changePassword(ChangePasswordWeb changePasswordWeb, User user);
}
