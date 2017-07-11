package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;

/**
 * Created by evgeny on 6/26/2017.
 */
public interface UserLanguageService {
    Language findUserLanguage(User user);
    void updateUserLanguage(UserLanguageWeb userAccount, User user);
}
