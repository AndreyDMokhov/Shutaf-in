package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserAccountService;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserLanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/users/settings")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserLanguageService userLanguageService;
    @Autowired
    private UserInfoService userInfoService;


    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb updateUserAccountProfileImage(@AuthenticatedUser Long userId,
                                                             @RequestBody @Valid AccountUserImageWeb userImageWeb,
                                                             BindingResult result) {
        log.debug("/users/settings/image update");
        checkBindingResult(result);
        return userAccountService.updateProfileImage(userId, userImageWeb);
    }

    @RequestMapping(value = "/image", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@AuthenticatedUser Long userId) {
        log.debug("/users/settings/image delete");
        userAccountService.deleteUserAccountProfileImage(userId);
    }

    @RequestMapping(value = "/language", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid AccountUserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @AuthenticatedUser Long userId) {
        log.debug("/users/settings/language");
        checkBindingResult(result);
        userLanguageService.updateUserLanguage(userLanguageWeb, userId);
    }


    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserInfoResponseDTO getUserInfo(@AuthenticatedUser Long userId) {

        return userInfoService.getUserInfo(userId);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@AuthenticatedUser Long userId,
                               @RequestBody @Valid AccountUserInfoRequest userInfoRequest,
                               BindingResult result) {

        checkBindingResult(result);
        userInfoService.updateUserInfo(userInfoRequest, userId);
    }

    private void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

}
