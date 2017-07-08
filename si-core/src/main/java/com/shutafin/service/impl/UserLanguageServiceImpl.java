package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.repository.UserAccountRepository;
import com.shutafin.service.UserLanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by evgeny on 6/26/2017.
 */
@Service
@Transactional
public class UserLanguageServiceImpl implements UserLanguageService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public Language findUserLanguage(User user) {
        if (user != null) {
            return userAccountRepository.findUserLanguage(user);
        }
        return null;
    }

    @Override
    @Transactional
    public void updateUserLanguage(UserLanguageWeb userLanguageWeb, User user) {
        userAccountRepository.updateUserLanguage(userLanguageWeb.getId(), user);
    }

}
