package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.UserAccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class UserAccountSettingsServiceImpl implements UserAccountSettingsService {


    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public void updateAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user) {
        updateFirstLastNames(user, userAccountSettingsWeb);
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
