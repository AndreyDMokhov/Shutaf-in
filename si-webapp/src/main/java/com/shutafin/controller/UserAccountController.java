package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.account.UserLanguageWeb;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.UserAccountService;
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


    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateAccountSettings(@RequestBody @Valid UserAccountSettingsWeb userAccountSettingsWeb,
                                      BindingResult result,
                                      @AuthenticatedUser User user) {

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userAccountService.updateAccountSettings(userAccountSettingsWeb, user);
    }


    @RequestMapping(value = "/image", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb updateUserAccountProfileImage(@AuthenticatedUser User user,
                                                      @RequestBody @Valid UserImageWeb userImageWeb,
                                                      BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        UserImage image = userAccountService.updateProfileImage(userImageWeb, user);
        return new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }


    @RequestMapping(value = "/image", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountProfileImage(@AuthenticatedUser User user) {

        userAccountService.deleteUserAccountProfileImage(user);
    }


    @RequestMapping(value = "/language", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void update(@RequestBody @Valid UserLanguageWeb userLanguageWeb,
                       BindingResult result,
                       @AuthenticatedUser User user) {

        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        userLanguageService.updateUserLanguage(userLanguageWeb, user);
    }


    @RequestMapping(value = "/language", method = RequestMethod.GET)
    public Language get(@AuthenticatedUser User user) {
        return userLanguageService.findUserLanguage(user);
    }

}
