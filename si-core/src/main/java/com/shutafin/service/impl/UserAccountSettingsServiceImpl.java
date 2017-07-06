package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserRepository;

import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.service.UserAccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;

/**
 * Created by usera on 6/29/2017.
 */

@Service
@Transactional
public class UserAccountSettingsServiceImpl implements UserAccountSettingsService {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @Override
    @Transactional
    public void save(UserAccountSettingsWeb userAccountSettingsWeb, User user) {
        if (updateFirstLastNames(user, userAccountSettingsWeb)) {
            userRepository.update(user);
        }
        updateLanguage(user, userAccountSettingsWeb);
    }

    private boolean updateFirstLastNames(User user, UserAccountSettingsWeb userAccountSettingsWeb) {
        String firstNameWeb = userAccountSettingsWeb.getFirstName();
        String lastNameWeb = userAccountSettingsWeb.getLastName();
        if(user.getFirstName().equals(firstNameWeb) && user.getLastName().equals(lastNameWeb)) {
            return false;
        }
        user.setFirstName(firstNameWeb);
        user.setLastName(lastNameWeb);
        return true;
    }

    private void updateLanguage(User user, UserAccountSettingsWeb userAccountSettingsWeb) {
        String languageId = userAccountSettingsWeb.getLanguageId();
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        String languageDB = userAccount.getLanguage().getDescription();
        if (!languageDB.equals(languageId)){
            List<Language> languages = languageRepository.findAll();
            for (Language language: languages) {
                if(language.getDescription().equals(languageId)){
                    userAccount.setLanguage(language);
                }
            }
            userAccountRepository.update(userAccount);
        }
    }

    @Override
    @Transactional
    public UserAccountSettingsWeb get(User user) {
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        Language language = userAccount.getLanguage();
        return new UserAccountSettingsWeb(user.getFirstName(), user.getLastName(), language.getDescription());
    }
}
