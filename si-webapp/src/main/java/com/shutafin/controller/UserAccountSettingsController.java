package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserAccountSettingsWeb;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserAccountSettingsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/users/settings")

public class UserAccountSettingsController {

    @Autowired
    private UserAccountSettingsService userAccountSettingsService;

    @Autowired
    private SessionManagementService sessionManagementService;


    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateAccountSettings(@RequestBody @Valid UserAccountSettingsWeb userAccountSettingsWeb, HttpServletRequest request, BindingResult result) {
        String sessionId = request.getHeader("session_id");
        if (StringUtils.isBlank(sessionId)) {
            throw new AuthenticationException();
        }
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userAccountSettingsService.updateAccountSettings(userAccountSettingsWeb, user);
    }

    @RequestMapping(value = "/image", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateUserAccountImage(@RequestHeader(value = "session_id") String sessionId,
                                       @RequestBody @Valid UserImageWeb userImageWeb, BindingResult result) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        userAccountSettingsService.updateUserAccountImage(userImageWeb, user);
    }

    @RequestMapping(value = "/image", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb getUserAccountImage(@RequestHeader(value = "session_id") String sessionId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserImage image = userAccountSettingsService.findUserAccountImage(user);
        if (image == null) {
            return null;
        }
        return new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @RequestMapping(value = "/image", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void removeUserAccountImage(@RequestHeader(value = "session_id") String sessionId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }

        userAccountSettingsService.deleteUserAccountImage(user);
    }

}
