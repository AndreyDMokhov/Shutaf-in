package com.shutafin.repository;


import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

public interface UserRepository extends PersistentDao<User> {

    User findByUserEmail(String email);
}
