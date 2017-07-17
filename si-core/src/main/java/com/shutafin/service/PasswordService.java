package com.shutafin.service;

import com.shutafin.model.entities.User;

/**
 * Created by Rogov on 04.07.2017.
 */
public interface PasswordService {

    void createAndSaveUserPassword(User user, String password);

    boolean isPasswordCorrect(User user, String password);

    void updateUserPasswordInDb(User user, String password);

}
