package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends BaseJpaRepository<UserCredentials, Long> {
    UserCredentials findByUser(User user);
}
