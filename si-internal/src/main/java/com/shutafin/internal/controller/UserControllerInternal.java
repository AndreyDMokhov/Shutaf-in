package com.shutafin.internal.controller;


import com.shutafin.annotations.InternalRestController;
import com.shutafin.internal.repository.UserRepositoryInternal;
import com.shutafin.model.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@InternalRestController
@RequestMapping("/users")
public class UserControllerInternal {

    @Autowired
    private UserRepositoryInternal userRepositoryInternal;

    @GetMapping(value = "/all")
    public List<User> getUsers() {


        return userRepositoryInternal.findAll();
    }
}
