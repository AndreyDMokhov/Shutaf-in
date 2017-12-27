package com.shutafin.service.impl;

import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserAccountControllerSender userAccountControllerSender;

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Override
    public AccountUserInfoResponseDTO getUserInfo(Long userId){
        return userAccountControllerSender.getUserInfo(userId);
    }

    @Override
    public void updateUserInfo(AccountUserInfoRequest userInfoRequest, Long userId) {
        List<ChatUser> allByUserId = chatUserRepository.findAllByUserId(userId);
        for (ChatUser chatUser : allByUserId) {
            chatUser.setFirstName(userInfoRequest.getFirstName());
            chatUser.setLastName(userInfoRequest.getLastName());
        }
        userAccountControllerSender.updateUserInfo(userId, userInfoRequest);
    }

}
