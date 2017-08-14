package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserImageRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.UserAccountSettingsService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserAccountSettingsServiceImpl implements UserAccountSettingsService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private UserImageService userImageService;


    @Override
    @Transactional
    public void updateAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user) {
        updateFirstLastNames(user, userAccountSettingsWeb);
    }

    @Override
    @Transactional
    public void updateUserAccountImage(UserImageWeb userImageWeb, User user) {
        UserImage userImage = null;
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);

        if (userImageWeb.getId() != null) {
           try {
               userImage = userImageService.getUserImage(user, userImageWeb.getId());
           } catch (ResourceNotFoundException exp) {
               //Simply skip. Security does not apply
           }
        }

        if (userImage == null) {
            userImage = userImageService.addUserImage(userImageWeb, user);
        }

        if (userAccount != null) {
            userAccount.setUserImage(userImage);
            userAccountRepository.updateUserAccountImage(userImage, user);
        }
    }

    @Override
    public UserImage findUserAccountImage(User user) {
        Long userImageId = userAccountRepository.findUserAccountImageId(user);
        return userImageService.getUserImage(user, userImageId);
    }

    @Override
    public void deleteUserAccountImage(User user) {
        UserAccount userAccount = userAccountRepository.findUserAccountByUser(user);
        userAccount.setUserImage(null);
        userAccountRepository.deleteUserAccountImage(user);
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
