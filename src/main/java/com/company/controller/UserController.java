package com.company.controller;

import com.company.model.User;
import com.company.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/users/save", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody User user) {
        userService.save(user);
    }
    @RequestMapping(value = "/users/update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody User user) {

        userService.update(user);
    }

    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public User getUserById(@PathVariable("id") Integer userId) {
        return userService.findByUserId(userId);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getUsers() {
        return userService.findAll();
    }
}
