package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 6/19/2017.
 */
@Deprecated
public interface UserCredentialsRepository extends BaseJpaRepository<UserCredentials, Long> {

    UserCredentials findByUser(User user);
}
