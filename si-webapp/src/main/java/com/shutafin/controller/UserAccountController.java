package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.model.web.user.UserImageWeb;
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
    public UserImageWeb updateUserAccountProfileImage(@AuthenticatedUser User user,
                                                      @RequestBody @Valid UserImageWeb userImageWeb,
                                                      BindingResult result) {
        log.debug("/users/settings/image");
        checkBindingResult(result);
        UserImage image = userAccountService.updateProfileImage(userImageWeb, user);
        return new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @RequestMapping(value = "/image", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@AuthenticatedUser User user) {
        log.debug("/users/settings/image");
        userAccountService.deleteUserAccountProfileImage(user);
    }

    @RequestMapping(value = "/language", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @AuthenticatedUser User user) {
        log.debug("/users/settings/language");
        checkBindingResult(result);
        userLanguageService.updateUserLanguage(userLanguageWeb, user);
    }

    @RequestMapping(value = "/language", method = RequestMethod.GET)
    public Language get(@AuthenticatedUser User user) {
        log.debug("/users/settings/language");
        return userLanguageService.findUserLanguage(user);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserInfoResponse getUserInfo(@AuthenticatedUser User user) {

        return userInfoService.getUserInfo(user);
    }

    @RequestMapping(value = "/info", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserInfo(@AuthenticatedUser User user,
                               @RequestBody @Valid UserInfoRequest userInfoRequest,
                               BindingResult result) {

        checkBindingResult(result);
        userInfoService.updateUserInfo(userInfoRequest, user);
    }

    private void checkBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
    }

}
