package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.repository.base.PersistentDao;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface UserLoginLogRepository extends PersistentDao<UserLoginLog> {
    Long countLoginFails(User user);
}
