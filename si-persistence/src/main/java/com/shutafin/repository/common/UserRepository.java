package com.shutafin.repository.common;


import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

public interface UserRepository extends PersistentDao<User> {

    User findUserByEmail(String email);
    Boolean isEmailExists(String email);
}
