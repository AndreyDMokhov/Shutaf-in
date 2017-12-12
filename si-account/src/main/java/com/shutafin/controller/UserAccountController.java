package com.shutafin.controller;

import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserInfoService;
import com.shutafin.core.service.UserLanguageService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.user.UserInfoRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/users")
public class UserAccountController {

    private UserAccountService userAccountService;
    private UserLanguageService userLanguageService;
    private UserInfoService userInfoService;
    private UserService userService;

    @Autowired
    public UserAccountController(
            UserAccountService userAccountService,
            UserLanguageService userLanguageService,
            UserInfoService userInfoService,
            UserService userService) {
        this.userAccountService = userAccountService;
        this.userLanguageService = userLanguageService;
        this.userInfoService = userInfoService;
        this.userService = userService;
    }

    @PostMapping(value = "/{userId}/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb updateUserAccountProfileImage(@PathVariable("userId") Long userId,
                                                             @RequestBody @Valid AccountUserImageWeb userImageWeb,
                                                             BindingResult result) {
        log.debug("/users/{}/profile-image", userId);
        checkBindingResult(result);

        //todo ms-account
        UserImage image = userAccountService.updateProfileImage(userImageWeb, userService.findUserById(userId));
        return new AccountUserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @GetMapping(value = "/{userId}/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getUserAccountProfileImage(@PathVariable("userId") Long userId) {
        log.debug("/{userId}/profile-image", userId);
        //todo ms-account
        User user = userService.findUserById(userId);
        //todo ms-account
        UserImage image = userAccountService.findUserAccountProfileImage(user);
        return new AccountUserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @DeleteMapping(value = "/{userId}/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@PathVariable("userId") Long userId) {
        log.debug("/users/{}/image", userId);
        userAccountService.deleteUserAccountProfileImage(userService.findUserById(userId));
    }



    @PutMapping(value = "/{userId}/language", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid AccountUserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @PathVariable("userId") Long userId) {
        log.debug("/users/settings/language");
        checkBindingResult(result);
        userLanguageService.updateUserLanguage(userLanguageWeb, userService.findUserById(userId));
    }

    @GetMapping(value = "/{userId}/language")
    public LanguageWeb getUserLanguage(@PathVariable("userId") Long userId) {
        log.debug("/users/settings/language");
        return userLanguageService.findUserLanguageWeb(userService.findUserById(userId));
    }

    @GetMapping(value = "/{userId}/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserInfoResponseDTO getUserInfo(@PathVariable("userId") Long userId) {
        return userInfoService.getUserInfo(userService.findUserById(userId));
    }

    @PostMapping(value = "/{userId}/info", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@PathVariable("userId") Long userId,
                               @RequestBody @Valid UserInfoRequest userInfoRequest,
                               BindingResult result) {

        checkBindingResult(result);
        userInfoService.updateUserInfo(userInfoRequest, userService.findUserById(userId));
    }

    private void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

}
