package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserLanguageService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.repository.account.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserLanguageServiceImpl implements UserLanguageService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private LanguageRepository languageRepository;


    @Override
    @Transactional(readOnly = true)
    public LanguageWeb findUserLanguageWeb(User user) {
        if (user != null) {
            Language language = userAccountRepository.findByUser(user).getLanguage();
            return LanguageWeb.builder()
                    .id(language.getId())
                    .description(language.getDescription())
                    .isActive(language.getIsActive())
                    .languageNativeName(language.getLanguageNativeName())
                    .build();
        }
        return null;
    }

    @Override
    public Language findUserLanguage(User user) {
        if (user != null) {
            return userAccountRepository.findByUser(user).getLanguage();
        }
        return null;
    }

    @Override
    @Transactional
    public void updateUserLanguage(AccountUserLanguageWeb userLanguageWeb, User user) {
        Language language = languageRepository.findOne(userLanguageWeb.getId());
        UserAccount userAccount = userAccountRepository.findByUser(user);
        userAccount.setLanguage(language);
    }

}
