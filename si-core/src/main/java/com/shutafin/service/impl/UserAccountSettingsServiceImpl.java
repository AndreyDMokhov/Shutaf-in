package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserImageRepository;
import com.shutafin.repository.UserRepository;
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
            userImage = userImageRepository.findById(userImageWeb.getId());
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
        return userAccountRepository.findUserAccountImage(user);
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
