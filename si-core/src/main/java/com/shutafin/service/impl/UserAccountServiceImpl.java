package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserRepository;
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

    private UserRepository userRepository;
    private UserAccountRepository userAccountRepository;
    private UserImageService userImageService;

    @Autowired
    public UserAccountServiceImpl(
            UserRepository userRepository,
            UserAccountRepository userAccountRepository,
            UserImageService userImageService) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
        this.userImageService = userImageService;
    }

    @Override
    @Transactional
    public void updateAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user) {
        updateFirstLastNames(user, userAccountSettingsWeb);
    }

    @Override
    @Transactional
    public UserImage updateProfileImage(UserImageWeb userImageWeb, User user) {
        UserImage userImage = null;
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);

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
            userImage = userImageService.addUserImage(userImageWeb, user);
        }

        if (userAccount != null) {
            userAccount.setUserImage(userImage);
            userAccountRepository.updateUserAccountImage(userImage, user);
        }
        return userImage;
    }

    @Override
    public UserImage findUserAccountProfileImage(User user) {
        Long userImageId = userAccountRepository.findUserAccountImageId(user);
        if (userImageId == null) {
            return null;
        }
        return userImageService.getUserImage(user, userImageId);
    }

    @Override
    public void deleteUserAccountProfileImage(User user) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        userAccount.setUserImage(null);
        userAccountRepository.update(userAccount);
    }

    private void updateFirstLastNames(User user, UserAccountSettingsWeb userAccountSettingsWeb) {
        String firstNameWeb = userAccountSettingsWeb.getFirstName();
        String lastNameWeb = userAccountSettingsWeb.getLastName();

        if (user.getFirstName().equals(firstNameWeb) && user.getLastName().equals(lastNameWeb)) {
            return;
        }

        user.setFirstName(firstNameWeb);
        user.setLastName(lastNameWeb);
        userRepository.update(user);
    }

}
