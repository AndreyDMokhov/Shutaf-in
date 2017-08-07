package com.shutafin.repository.common;


import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

public interface UserRepository extends PersistentDao<User> {

    User findUserByEmail(String email);
    Boolean isEmailExists(String email);
    List<User> findUsersByFirstAndLastName(String firstName, String lastName);
    List<User> findUsersByName(String name);
}
