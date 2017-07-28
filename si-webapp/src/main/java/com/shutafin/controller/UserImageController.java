package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/images")
public class UserImageController {

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb getUserImage(@RequestHeader(value = "session_id", required = false) String sessionId,
                                     @PathVariable(value = "id") Long userImageId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserImage image = userImageService.getUserImage(user, userImageId);
        if (image == null) {
            return null;
        }

        return new UserImageWeb(image.getImageStorage().getImageEncoded(), image.getCreatedDate().toString());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserImage(@RequestHeader(value = "session_id", required = false) String sessionId,
                             @RequestBody @Valid UserImageWeb image, BindingResult result) {

        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        userImageService.addUserImage(image, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@RequestHeader(value = "session_id") String sessionId,
                                @PathVariable(value = "id") Long userImageId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        userImageService.deleteUserImage(user, userImageId);
    }
}
