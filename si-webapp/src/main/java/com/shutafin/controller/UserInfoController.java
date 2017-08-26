package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/info")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserInfoWeb getUserInfo(@AuthenticatedUser User user) {

        UserInfo userInfo = userInfoService.findUserInfo(user);
        if (userInfo == null) {
            return null;
        }

        return new UserInfoWeb(user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                userInfo.getCurrentCity().getId(),
                userInfo.getGender().getId(),
                userInfo.getFacebookLink(),
                userInfo.getProfession(),
                userInfo.getCompany(),
                userInfo.getPhoneNumber());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@AuthenticatedUser User user,
                               @RequestBody UserInfoWeb userInfoWeb) {

        userInfoService.updateUserInfo(userInfoWeb, user);
    }
}
