package com.company.service;

import com.company.model.User;

import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    User findByUserId(Integer userId);
    List<User> findAll();
}
