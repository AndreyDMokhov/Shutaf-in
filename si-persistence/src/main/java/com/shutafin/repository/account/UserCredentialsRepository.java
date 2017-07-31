package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.repository.base.PersistentDao;

/**
 * Created by evgeny on 6/19/2017.
 */
public interface UserCredentialsRepository extends PersistentDao<UserCredentials> {
    UserCredentials findUserByUserId(User user);
}
