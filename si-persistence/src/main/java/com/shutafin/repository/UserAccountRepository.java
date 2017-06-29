package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.base.PersistentDao;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface UserAccountRepository extends PersistentDao<UserAccount> {
    public Language getUserLanguage(User user);
    public void updateUserLanguage(Integer languageId, User user);
}
