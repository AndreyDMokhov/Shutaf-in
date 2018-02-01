package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserInfoResponseDTO;
import com.shutafin.model.web.account.AccountUserLanguageWeb;
import com.shutafin.model.web.initialization.InitializationResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserAccountService;
import com.shutafin.service.UserInfoService;
import com.shutafin.service.UserLanguageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping(value = "/image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb updateUserAccountProfileImage(@AuthenticatedUser Long userId,
                                                             @RequestBody @Valid AccountUserImageWeb userImageWeb,
                                                             BindingResult result) {
        log.debug("/users/settings/image update");
        checkBindingResult(result);
        return userAccountService.updateProfileImage(userId, userImageWeb);
    }

    @DeleteMapping(value = "/image", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@AuthenticatedUser Long userId) {
        log.debug("/users/settings/image delete");
        userAccountService.deleteUserAccountProfileImage(userId);
    }

    @PutMapping(value = "/language", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid AccountUserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @AuthenticatedUser Long userId) {
        log.debug("/users/settings/language");
        checkBindingResult(result);
        userLanguageService.updateUserLanguage(userLanguageWeb, userId);
    }


    @GetMapping(value = "/info", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserInfoResponseDTO getUserInfo(@AuthenticatedUser Long userId) {
        return userInfoService.getUserInfo(userId);
    }

    @PostMapping(value = "/info", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public InitializationResponse updateUserInfo(@AuthenticatedUser Long userId,
                                                 @RequestBody @Valid AccountUserInfoRequest userInfoRequest,
                                                 BindingResult result) {
        checkBindingResult(result);
        return userInfoService.updateUserInfo(userInfoRequest, userId);
    }

    private void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

}
