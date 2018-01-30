package com.shutafin.controller;

import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.account.AccountUserImageWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/images")
@Slf4j
public class UserImageController {

    @Autowired
    private UserImageService userImageService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getCompressedUserImageById(@AuthenticatedUser Long authenticatedUserId, @PathVariable(value = "userId") Long userId) {
        log.debug("/images/{userId}");
        return userImageService.getCompressedUserImage(userId);
    }

    @RequestMapping(value = "/original/{userId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getOriginalUserImageById(@AuthenticatedUser Long authenticatedUserId, @PathVariable(value = "userId") Long userId) {
        log.debug("/images/original/{userId}");
        return userImageService.getOriginalUserImage(userId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb addUserImage(@AuthenticatedUser Long authenticatedUserId,
                                            @RequestBody @Valid AccountUserImageWeb image,
                                            BindingResult result) {
        log.debug("/images/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return userImageService.addUserImage(image, authenticatedUserId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@AuthenticatedUser Long authenticatedUserId,
                                @PathVariable(value = "id") Long userImageId) {
        log.debug("/images/{id}");
        userImageService.deleteUserImage(authenticatedUserId, userImageId);
    }
}
