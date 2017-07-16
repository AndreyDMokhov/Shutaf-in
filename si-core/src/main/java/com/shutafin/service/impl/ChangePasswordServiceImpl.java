package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.ChangePasswordWeb;
import com.shutafin.service.ChangePasswordService;
import com.shutafin.service.PasswordService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChangePasswordServiceImpl implements ChangePasswordService{

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private PasswordService passwordService;

    @Transactional
    @Override
    public void changePassword(ChangePasswordWeb changePasswordWeb, String session_id) {
        User user = sessionManagementService.findUserWithValidSession(session_id);
        if (user == null){
            throw new AuthenticationException();
        }
        if (passwordService.isPasswordCorrect(user,changePasswordWeb.getOldPassword())){
            passwordService.updateUserPasswordInDb(user, changePasswordWeb.getNewPassword());
        }
    }
}
