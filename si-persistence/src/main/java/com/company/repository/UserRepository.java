package com.company.repository;


import com.company.model.User;

import java.util.List;

public interface UserRepository {

    void save(User user);
    void update(User user);
    User findByUserId(Integer userId);
    List<User> findAll();
}
