package com.shutafin.core.service.impl;

import com.shutafin.core.service.ChangePasswordService;
import com.shutafin.core.service.PasswordService;
import com.shutafin.model.entities.User;
import com.shutafin.model.exception.exceptions.IncorrectPasswordException;
import com.shutafin.model.web.account.AccountChangePasswordWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class ChangePasswordServiceImpl implements ChangePasswordService {

    @Autowired
    private PasswordService passwordService;

    @Override
    public void changePassword(AccountChangePasswordWeb changePasswordWeb, User user) {
        if (!passwordService.isPasswordCorrect(user, changePasswordWeb.getOldPassword())) {
            log.warn("Authentication exception:");
            log.warn("User password with ID {} is incorrect", user.getId());
            throw new IncorrectPasswordException();
        }
        passwordService.updateUserPasswordInDb(user, changePasswordWeb.getNewPassword());
    }
}
