package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {
    UserAccount findByUser(User user);

}
