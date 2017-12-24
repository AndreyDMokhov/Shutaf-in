package com.shutafin.service.impl;

import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.sender.account.UserImageControllerSender;
import com.shutafin.service.UserImageService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Base64;

@Service
@Transactional
@Slf4j
public class UserImageServiceImpl implements UserImageService {

    private static final String DEFAULT_AVATAR = "default_avatar.jpg";



    @Autowired
    private UserImageControllerSender userImageControllerSender;

    @Override
    @SneakyThrows
    public AccountUserImageWeb addUserImage(AccountUserImageWeb image, Long userId) {
        return userImageControllerSender.addUserImage(userId, image);
    }

    @Override
    public AccountUserImageWeb getDefaultUserImage(Long userId) {
        return userImageControllerSender.getDefaultUserImageByUserId(userId);
    }

    @Override
    public AccountUserImageWeb getOriginalUserImage(Long userId) {
        return userImageControllerSender.getOriginalUserImageByUserId(userId);
    }

    @Override
    public void deleteUserImage(Long userId, Long userImageId) {
        userImageControllerSender.deleteUserImage(userId, userImageId);
    }


    @Override
    @SneakyThrows
    //todo remote this after email notification
    public String getDefaultImageBase64() {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(DEFAULT_AVATAR);

        if (resourceAsStream != null) {
            byte[] imageDecoded = new byte[resourceAsStream.available()];
            resourceAsStream.read(imageDecoded);
            resourceAsStream.close();
            return Base64.getEncoder().encodeToString(imageDecoded);
        }

        return null;

    }



}
