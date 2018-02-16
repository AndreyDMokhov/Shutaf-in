package com.shutafin.controller;

import com.shutafin.core.service.UserAccountService;
import com.shutafin.core.service.UserInfoService;
import com.shutafin.core.service.UserLanguageService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.*;
import com.shutafin.model.web.common.LanguageWeb;
import com.shutafin.model.web.common.UserSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


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

    @PostMapping(value = "/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb updateUserAccountProfileImage(@RequestParam("userId") Long userId,
                                                             @RequestBody @Valid AccountUserImageWeb userImageWeb,
                                                             BindingResult result) {
        log.debug("/users/profile-image", userId);
        checkBindingResult(result);

        UserImage image = userAccountService.updateProfileImage(userImageWeb, userService.findUserById(userId));
        return new AccountUserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime(),
                image.getUser().getFirstName(),
                image.getUser().getLastName(),
                image.getUser().getId());
    }

    @GetMapping(value = "/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getUserAccountProfileImage(@RequestParam("userId") Long userId) {
        log.debug("/profile-image", userId);
        User user = userService.findUserById(userId);
        UserImage image = userAccountService.findUserAccountProfileImage(user);
        return AccountUserImageWeb
                .builder()
                .id(image.getId())
                .image(image.getImageStorage().getImageEncoded())
                .createdDate(image.getCreatedDate().getTime())
                .firstName(image.getUser().getFirstName())
                .lastName(image.getUser().getLastName())
                .userId(image.getUser().getId())
                .build();
    }

    @GetMapping(value = "/profile-images", produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<AccountUserImageWeb> getUserAccountProfileImages(@RequestParam("userIds") List<Long> userIds) {
        log.debug("/profile-images", userIds);
        List<User> users = userService.findUsersByIds(userIds);


        List<AccountUserImageWeb> userImages = new ArrayList<>();
        for (User user : users) {
            UserImage profileImage = userAccountService.findUserAccountProfileImage(user);
            if (profileImage == null) {
                userImages.add(AccountUserImageWeb
                        .builder()
                        .id(null)
                        .image(null)
                        .createdDate(null)
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userId(user.getId())
                        .build());
            } else {
                userImages.add(AccountUserImageWeb
                        .builder()
                        .id(profileImage.getId())
                        .image(profileImage.getImageStorage().getImageEncoded())
                        .createdDate(profileImage.getCreatedDate().getTime())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .userId(user.getId())
                        .build());
            }

        }
        return userImages;
    }

    @DeleteMapping(value = "/profile-image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@RequestParam("userId") Long userId) {
        log.debug("/users/image", userId);
        userAccountService.deleteUserAccountProfileImage(userService.findUserById(userId));
    }


    @PutMapping(value = "/language", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserLanguage(@RequestBody @Valid AccountUserLanguageWeb userLanguageWeb,
                                   BindingResult result,
                                   @RequestParam("userId") Long userId) {
        log.debug("/users/settings/language");
        checkBindingResult(result);
        userLanguageService.updateUserLanguage(userLanguageWeb, userService.findUserById(userId));
    }

    @GetMapping(value = "/language")
    public LanguageWeb getUserLanguage(@RequestParam("userId") Long userId) {
        log.debug("/users/settings/language");
        return userLanguageService.findUserLanguageWeb(userService.findUserById(userId));
    }

    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserInfoResponseDTO getUserInfo(@RequestParam("userId") Long userId) {
        return userInfoService.getUserInfo(userService.findUserById(userId));
    }

    @PutMapping(value = "/info", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@RequestParam("userId") Long userId,
                               @RequestBody @Valid AccountUserInfoRequest userInfoRequest,
                               BindingResult result) {

        checkBindingResult(result);
        userInfoService.updateUserInfo(userInfoRequest, userService.findUserById(userId));
    }

    @GetMapping(value = "/info-base")
    public AccountUserWeb getBaseInfo(@RequestParam("userId") Long userId) {
        return userService.getAccountUserWebById(userId);
    }

    @GetMapping(value = "/account-status")
    public AccountStatus updateUserAccountStatus(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "enforce", required = false, defaultValue = "false") Boolean isEnforcedChange) {

        AccountStatus incomingAccountStatus = AccountStatus.getById(status);
        AccountStatus userAccountStatus = userAccountService.getUserAccountStatus(userService.findUserById(userId));
        if (status == null) {
            return userAccountStatus;
        }

        if ((incomingAccountStatus.getCode() > userAccountStatus.getCode()) || Boolean.TRUE.equals(isEnforcedChange)) {

            return userAccountService.updateUserAccountStatus(incomingAccountStatus.getCode(), userService.findUserById(userId));
        }

        return userAccountStatus;
    }


    @GetMapping(value = "/info-bases")
    public List<AccountUserWeb> getBaseInfos(@RequestParam("userIds") List<Long> userIds) {
        List<AccountUserWeb> accountUserWebs = new ArrayList<>();
        for (Long userId : userIds) {
            accountUserWebs.add(getBaseInfo(userId));
        }
        return accountUserWebs;
    }

    @GetMapping(value = "/info-search")
    public UserSearchResponse getUserSearchObject(@RequestParam("userId") Long userId) {
        return userInfoService.findUserSearchInfo(userId);
    }

    private void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

}
