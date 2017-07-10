package com.shutafin.service;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;

/**
 * Created by Rogov on 04.07.2017.
 */
public interface PasswordService {

    public void saveUserPasswordToDb (User user, String password);

    public boolean isPasswordCorrect(User user, String password);

    public void updateUserPasswordInDb(User user, String password);

}
