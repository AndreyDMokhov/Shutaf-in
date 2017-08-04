package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserInfo;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public APIWebResponse getUserInfo(@RequestHeader(value = "session_id") String sessionId) {

        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }

        UserInfo userInfo = userInfoService.findUserInfo(user);
        if (userInfo == null) {
            return null;
        }

        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(new UserInfoWeb(userInfo.getUser().getId(),
                userInfo.getCurrentCity().getId(),
                userInfo.getGender().getId(),
                userInfo.getFacebookLink(),
                userInfo.getProfession(),
                userInfo.getCompany(),
                userInfo.getPhoneNumber()));

        return apiWebResponse;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@RequestHeader(value = "session_id") String sessionId,
                                         @RequestBody UserInfoWeb userInfoWeb) {

        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }

        userInfoService.updateUserInfo(userInfoWeb, user);
    }
}
