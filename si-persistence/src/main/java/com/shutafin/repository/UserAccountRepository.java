package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.base.PersistentDao;


public interface UserAccountRepository extends PersistentDao<UserAccount> {
    Language findUserLanguage(User user);
    void updateUserLanguage(Integer languageId, User user);
    UserAccount findUserAccountByUser(User user);
}
