package com.shutafin.service;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;

/**
 * Created by Rogov on 04.07.2017.
 */
public interface PasswordService {

    public void saveUserPasswordToDb (User user, String password);

    public void checkUserPassword (User user, String password) throws AuthenticationException;

}
