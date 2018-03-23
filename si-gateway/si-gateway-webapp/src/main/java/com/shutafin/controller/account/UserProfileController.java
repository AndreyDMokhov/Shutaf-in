package com.shutafin.controller.account;

import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.processors.annotations.authentication.NoAuthentication;
import com.shutafin.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by evgeny on 10/24/2017.
 */
@RestController
@RequestMapping("/users/profile")
public class UserProfileController {

    @Autowired
    private UserInfoService userInfoService;

    @NoAuthentication
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserInfoResponseDTO getUserInfo(@PathVariable(value = "userId") Long userId){
        return userInfoService.getUserInfo(userId);
    }
}
