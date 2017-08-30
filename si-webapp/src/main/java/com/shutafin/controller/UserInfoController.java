package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInitializationService userInitializationService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserInfoResponse getUserInfo(@AuthenticatedUser User user) {

        return userInitializationService.getUserInitializationData(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@AuthenticatedUser User user,
                               @RequestBody @Valid UserInfoWeb userInfoWeb,
                               BindingResult result) {

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userInfoService.updateUserInfo(userInfoWeb, user);
    }
}
