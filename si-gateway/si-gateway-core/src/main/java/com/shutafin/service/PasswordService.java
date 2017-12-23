package com.shutafin.service;

import com.shutafin.model.entities.User;

/**
 * Created by Rogov on 04.07.2017.
 */
public interface PasswordService {

    boolean isPasswordCorrect(User user, String password);

    void updateUserPasswordInDb(User user, String password);

}
