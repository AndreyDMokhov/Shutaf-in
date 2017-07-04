package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.web.user.ChangePasswordWeb;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.service.ChangePasswordService;
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
    private UserCredentialsRepository userCredentialsRepository;

    @Transactional
    @Override
    public String changePassword(ChangePasswordWeb changePasswordWeb, String session_id) {
        User user = sessionManagementService.findUserWithValidSession(session_id);
        if (user == null){
            throw new AuthenticationException();
        }
        UserCredentials userCredentials = userCredentialsRepository.findUserByUserId(user);
        if (!userCredentials.getPasswordHash().equals(changePasswordWeb.getOldPassword())){
            throw new AuthenticationException();
        }
        userCredentials.setPasswordHash(changePasswordWeb.getNewPassword());
        return sessionManagementService.generateNewSession(user);
    }
}
