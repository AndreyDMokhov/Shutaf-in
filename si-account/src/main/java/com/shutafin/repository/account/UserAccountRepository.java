package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.types.AccountStatus;
import com.shutafin.model.types.AccountType;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends BaseJpaRepository<UserAccount, Long>, UserAccountRepositoryCustom {
    UserAccount findByUser(User user);
    List<UserAccount> findAllByAccountStatusAndAccountType(AccountStatus accountStatus, AccountType accountType);

}
