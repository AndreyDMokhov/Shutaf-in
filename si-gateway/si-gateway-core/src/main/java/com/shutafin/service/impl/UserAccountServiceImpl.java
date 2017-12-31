package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.UserAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {


    @Autowired
    private UserAccountControllerSender userAccountControllerSender;


    @Override
    @Transactional
    public AccountUserImageWeb updateProfileImage(Long userId, AccountUserImageWeb userImageWeb) {

        return userAccountControllerSender.updateUserAccountProfileImage(userId, userImageWeb);
    }

    @Override
    public AccountUserImageWeb findUserAccountProfileImage(Long userId) {
        return userAccountControllerSender.getUserAccountProfileImage(userId);
    }

    @Override
    public void deleteUserAccountProfileImage(Long userId) {
        userAccountControllerSender.deleteUserAccountProfileImage(userId);
    }

}
