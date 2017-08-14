package com.shutafin.service;

import com.shutafin.model.entities.User;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findPartners(User user);
}
