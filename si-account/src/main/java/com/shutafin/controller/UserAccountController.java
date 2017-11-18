package com.shutafin.controller;

import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserInfoService;
import com.shutafin.core.service.UserLanguageService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.infrastructure.Language;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.model.web.user.UserLanguageWeb;
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

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserLanguageService userLanguageService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserService userService;


    @PostMapping(value = "/{userId}/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb updateUserAccountProfileImage(@PathVariable("userId") Long userId,
                                                      @RequestBody @Valid UserImageWeb userImageWeb,
                                                      BindingResult result) {
        log.debug("/users/{}/profile-image", userId);
        checkBindingResult(result);
        UserImage image = userAccountService.updateProfileImage(userImageWeb, userService.findUserById(userId));
        return new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @DeleteMapping(value = "/{userId}/image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@PathVariable("userId") Long userId) {
        log.debug("/users/{}/image", userId);
        userAccountService.deleteUserAccountProfileImage(userService.findUserById(userId));
    }

    @PutMapping(value = "/{userId}/language", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @PathVariable("userId") Long userId) {
        log.debug("/users/settings/language");
        checkBindingResult(result);
        userLanguageService.updateUserLanguage(userLanguageWeb, userService.findUserById(userId));
    }

    @GetMapping(value = "/{userId}/language")
    public Language get(@PathVariable("userId") Long userId) {
        log.debug("/users/settings/language");
        return userLanguageService.findUserLanguage(userService.findUserById(userId));
    }

    @GetMapping(value = "/{userId}/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserInfoResponseDTO getUserInfo(@PathVariable("userId") Long userId) {
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
