package com.shutafin.service;

import com.shutafin.model.web.account.AccountUserLanguageWeb;

/**
 * Created by evgeny on 6/26/2017.
 */
public interface UserLanguageService {
    void updateUserLanguage(AccountUserLanguageWeb userAccount, Long userId);
}
