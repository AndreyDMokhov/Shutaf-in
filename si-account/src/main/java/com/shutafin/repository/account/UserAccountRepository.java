package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import com.shutafin.model.web.account.AccountStatus;
import com.shutafin.model.types.AccountType;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAccountRepository extends BaseJpaRepository<UserAccount, Long>, UserAccountRepositoryCustom {
    UserAccount findByUser(User user);
    UserAccount findByUserId (Long userId);
    List<UserAccount> findAllByAccountStatusAndAccountType(AccountStatus accountStatus, AccountType accountType);

    @Query("SELECT ucc.userImage.id FROM UserAccount ucc where ucc.user.id = :userId")
    Long findDefaultUserImageIdByUserId(@Param("userId") Long userId);
}
