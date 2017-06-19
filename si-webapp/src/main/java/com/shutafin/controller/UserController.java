package com.shutafin.controller;

import com.shutafin.exception.exceptions.InputValidationException;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void save(@RequestBody @Valid UserInfoWeb user, BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userService.save(user);
    }


    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserInfoWeb user, BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userService.update(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserInfoWeb getUserById(@PathVariable("id") Long userId) {
        return userService.findByUserId(userId);
    }


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserInfoWeb> getUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
    }
}
