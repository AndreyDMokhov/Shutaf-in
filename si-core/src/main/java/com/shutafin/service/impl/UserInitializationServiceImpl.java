package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.initialization.custom.UserInitializationRepository;
import com.shutafin.service.UserImageService;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserInitializationServiceImpl implements UserInitializationService {


    @Autowired
    private UserInitializationRepository userInitializationRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    @Transactional
    public UserInfoResponse getUserInitializationData(User user) {

        UserInfoResponse userInfoResponse = userInitializationRepository.getUserInitializationData(user);
        Long userImageId = userAccountRepository.findUserAccountImageId(user);
        if (userImageId != null) {
            UserImage userImage = userImageService.getUserImage(user, userImageId);
            userInfoResponse.addUserImage(userImage);
        }
        UserInfo userInfo = userInfoService.findUserInfo(user);
        if (userInfo != null) {
            userInfoResponse.addUserInfo(userInfo);
        }

        return userInfoResponse;
    }
}
