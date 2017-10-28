package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.repository.base.BaseJpaRepository;


public interface UserAccountRepository extends BaseJpaRepository<UserAccount, Long>, UserAccountRepositoryCustom {

    UserAccount findByUser(User user);
}
