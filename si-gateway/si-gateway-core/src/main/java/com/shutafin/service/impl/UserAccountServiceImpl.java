package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.types.CompressionType;
import com.shutafin.model.entities.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.service.UserAccountService;
import com.shutafin.service.UserImageService;
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
    // TODO: MS-account UserAccountController.updateUserAccountProfileImage()
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
            userAccountRepository.updateUserAccountImage(userImage, user);
        }
        return userImage;
    }

    @Override
    // TODO: MS-account UserAccountController.getUserAccountProfileImage()
    public UserImage findUserAccountProfileImage(User user) {
        Long userImageId = userAccountRepository.findUserAccountImageId(user);

        return userImageId == null ? null : userImageService.getUserImage(user, userImageId);
    }

    @Override
    // TODO: MS-account UserAccountController.deleteUserAccountProfileImage()
    public void deleteUserAccountProfileImage(User user) {
        UserAccount userAccount = userAccountRepository.findByUser(user);
        userAccount.setUserImage(null);
        userAccountRepository.save(userAccount);
    }

}
