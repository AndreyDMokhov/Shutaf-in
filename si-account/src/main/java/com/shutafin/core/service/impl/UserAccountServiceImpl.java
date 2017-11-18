package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserImageService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.types.CompressionType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.account.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private UserAccountRepository userAccountRepository;
    private UserImageService userImageService;

    @Autowired
    public UserAccountServiceImpl(
            UserAccountRepository userAccountRepository,
            UserImageService userImageService) {
        this.userAccountRepository = userAccountRepository;
        this.userImageService = userImageService;
    }


    @Override
    @Transactional
    public UserImage updateProfileImage(UserImageWeb userImageWeb, User user) {
        UserImage userImage = null;
        UserAccount userAccount = userAccountRepository.findByUser(user);

        if (userImageWeb.getId() != null) {
            try {
                userImage = userImageService.getUserImage(user, userImageWeb.getId());
            } catch (ResourceNotFoundException exp) {
                log.warn("Resource not found exception:");
                log.warn("User Image with ID {} was not found", userImageWeb.getId());
                userImage = null;
            }
        }

        if (userImage == null) {
            userImage = userImageService.addUserImage(userImageWeb, user, PermissionType.PUBLIC, CompressionType.COMPRESSION_RATE_0_7);
        }

        if (userAccount != null) {
            userAccount.setUserImage(userImage);
        }
        return userImage;
    }

    @Override
    public UserImage findUserAccountProfileImage(User user) {
        return userAccountRepository.findByUser(user).getUserImage();
    }

    @Override
    public void deleteUserAccountProfileImage(User user) {
        UserAccount userAccount = userAccountRepository.findByUser(user);
        userAccount.setUserImage(null);
    }

}
