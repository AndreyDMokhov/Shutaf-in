package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.model.web.common.LanguageWeb;


public interface UserLanguageService {
    LanguageWeb findUserLanguageWeb(User user);
    Language findUserLanguage(User user);
    void updateUserLanguage(AccountUserLanguageWeb userAccount, User user);
}
