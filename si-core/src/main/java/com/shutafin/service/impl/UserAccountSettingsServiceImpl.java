package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.repository.UserRepository;

import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.service.UserAccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public UserAccountSettingsWeb getCurrentAccountSettings(User user) {
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        Language language = userAccount.getLanguage();
        return new UserAccountSettingsWeb(user.getFirstName(), user.getLastName(), language.getDescription());
    }

    @Override
    @Transactional
    public void saveNewAccountSettings(UserAccountSettingsWeb userAccountSettingsWeb, User user) {
        updateFirstLastNames(user, userAccountSettingsWeb);
        updateLanguage(user, userAccountSettingsWeb);
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

    private void updateLanguage(User user, UserAccountSettingsWeb userAccountSettingsWeb) {
        String languageDes = userAccountSettingsWeb.getLanguageDes();
        UserAccount userAccount = userAccountRepository.getAccountUserByUserId(user);
        String languageDB = userAccount.getLanguage().getDescription();
        if (!languageDB.equals(languageDes)) {
            List<Language> languages = languageRepository.findAll();
            for (Language language : languages) {
                if (language.getDescription().equals(languageDes)) {
                    userAccount.setLanguage(language);
                }
            }
            userAccountRepository.update(userAccount);
        }
    }
}
