package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.IncorrectPasswordException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.ChangePasswordWeb;
import com.shutafin.service.ChangePasswordService;
import com.shutafin.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangePasswordServiceImpl implements ChangePasswordService{



    @Autowired
    private PasswordService passwordService;

    @Transactional
    @Override
    public void changePassword(ChangePasswordWeb changePasswordWeb, User user) {
        if (!passwordService.isPasswordCorrect(user,changePasswordWeb.getOldPassword())){
            throw new IncorrectPasswordException();
        }
        passwordService.updateUserPasswordInDb(user, changePasswordWeb.getNewPassword());
    }
}
