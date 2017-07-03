package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.repository.base.PersistentDao;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface UserAccountRepository extends PersistentDao<UserAccount> {
    UserAccount findUserAccountByUser(User user);
}
