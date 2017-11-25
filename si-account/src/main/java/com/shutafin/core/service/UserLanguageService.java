package com.shutafin.core.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.user.UserLanguageWeb;


public interface UserLanguageService {
    Language findUserLanguage(User user);
    void updateUserLanguage(UserLanguageWeb userAccount, User user);
}
