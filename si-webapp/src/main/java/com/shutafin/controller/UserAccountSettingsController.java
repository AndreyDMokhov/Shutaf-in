package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserAccountSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/users/settings")
public class UserAccountSettingsController {

    @Autowired
    private UserAccountSettingsService userAccountSettingsService;


    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateAccountSettings(@RequestBody @Valid UserAccountSettingsWeb userAccountSettingsWeb,
                                      BindingResult result,
                                      @AuthenticatedUser User user) {

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userAccountSettingsService.updateAccountSettings(userAccountSettingsWeb, user);
    }

    @RequestMapping(value = "/image", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserAccountImage(@AuthenticatedUser User user,
                                       @RequestBody @Valid UserImageWeb userImageWeb,
                                       BindingResult result) {
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userAccountSettingsService.updateUserAccountImage(userImageWeb, user);
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb getUserAccountImage(@AuthenticatedUser User user) {

        UserImage image = userAccountSettingsService.findUserAccountImage(user);
        if (image == null) {
            return null;
        }
        return new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @RequestMapping(value = "/image", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserAccountImage(@AuthenticatedUser User user) {

        userAccountSettingsService.deleteUserAccountImage(user);
    }

}
