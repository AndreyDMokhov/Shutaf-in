package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.sender.account.UserImageControllerSender;
import com.shutafin.service.UserImageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserImageServiceImpl implements UserImageService {

    @Autowired
    private UserImageControllerSender userImageControllerSender;

    @Override
    @SneakyThrows
    public AccountUserImageWeb addUserImage(AccountUserImageWeb image, Long userId) {
        return userImageControllerSender.addUserImage(userId, image);
    }

    @Override
    public AccountUserImageWeb getUserImage(Long userId, Long userImageId) {

        return userImageControllerSender.getUserImage(userId, userImageId);
    }

    @Override
    public AccountUserImageWeb getUserImage(Long userId) {
        return userImageControllerSender.getUserImageByUserId(userId);
    }

    @Override
    public AccountUserImageWeb getOriginalUserImage(Long userId) {
        return userImageControllerSender.getOriginalUserImageByUserId(userId);
    }

    @Override
    public void deleteUserImage(Long userId, Long userImageId) {
        userImageControllerSender.deleteUserImage(userId, userImageId);
    }

}
