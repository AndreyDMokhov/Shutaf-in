package com.shutafin.persistence.repository.account;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserCredentials;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialsRepository extends CrudRepository<UserCredentials, Long> {
    UserCredentials findByUser(User user);
}
