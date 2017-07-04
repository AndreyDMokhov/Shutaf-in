package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.model.web.user.ResetPasswordWeb;
import com.shutafin.repository.UserCredentialsRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepository;

    @Transactional
    @Override
    public void resetPassword(ResetPasswordWeb resetPasswordWeb) {
        User user = userRepository.findUserByEmail(resetPasswordWeb.getEmail());
        if (user != null){
            UserCredentials userCredentials = userCredentialsRepository.findUserByUserId(user);
            userCredentials.setPasswordHash(resetPasswordWeb.getNewPassword());
        }
    }
}
