package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserAccountControllerSender userAccountControllerSender;


    @Override
    public AccountUserInfoResponseDTO getUserInfo(Long userId){
        return userAccountControllerSender.getUserInfo(userId);
    }

    @Override
    //todo move filters
    public void updateUserInfo(AccountUserInfoRequest userInfoRequest, Long userId) {
        userAccountControllerSender.updateUserInfo(userId, userInfoRequest);
    }

}
