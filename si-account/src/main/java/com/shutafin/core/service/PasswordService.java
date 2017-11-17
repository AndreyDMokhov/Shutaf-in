package com.shutafin.core.service;

import com.shutafin.model.entities.User;

public interface PasswordService {

    void createAndSaveUserPassword(User user, String password);

    boolean isPasswordCorrect(User user, String password);

    void updateUserPasswordInDb(User user, String password);

}
