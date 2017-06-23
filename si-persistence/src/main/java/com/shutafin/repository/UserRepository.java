package com.shutafin.repository;


import com.shutafin.model.entities.User;
import com.shutafin.model.web.LoginWebModel;
import com.shutafin.repository.base.PersistentDao;

public interface UserRepository extends PersistentDao<User> {

    public User findUserByEmail(String email);
}
