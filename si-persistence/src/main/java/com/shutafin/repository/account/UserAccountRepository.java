package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.types.LanguageEnum;
import com.shutafin.repository.base.PersistentDao;


public interface UserAccountRepository extends PersistentDao<UserAccount> {
    Language findUserLanguage(User user);
    void updateUserLanguage(LanguageEnum language, User user);
    UserAccount findUserAccountByUser(User user);
}
