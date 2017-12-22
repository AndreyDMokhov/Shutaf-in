package com.shutafin.controller;

import com.shutafin.core.service.UserImageService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.types.CompressionType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.account.AccountUserImageWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserImageController {

    private UserImageService userImageService;
    private UserService userService;

    @Autowired
    public UserImageController(
            UserImageService userImageService,
            UserService userService) {
        this.userImageService = userImageService;
        this.userService = userService;
    }

    @GetMapping(value = "/{userId}/images/{userImageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getUserImage(@PathVariable("userId") Long userId,
                                            @PathVariable(value = "userImageId") Long userImageId) {
        log.debug("/images/{id}");
        UserImage image = userImageService.getUserImage(userService.findUserById(userId), userImageId);
        return new AccountUserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @GetMapping(value = "/{userId}/images", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getUserImageByUserId(@PathVariable("userId") Long userId) {
        log.debug("/images/byId/{id}");
        UserImage image = userImageService.getUserImage(userService.findUserById(userId));
        return new AccountUserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @GetMapping(value = "/{userId}/images/original", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getOriginalUserImageByUserId(@PathVariable("userId") Long userId) {
        log.debug("/images/original/{id}");
        UserImage image = userImageService.getOriginalUserImage(userService.findUserById(userId));
        return new AccountUserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @PostMapping(value = "/{userId}/images", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb addUserImage(@PathVariable("userId") Long userId,
                                            @RequestBody @Valid AccountUserImageWeb image, BindingResult result) {
        log.debug("/images/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        UserImage userImage = userImageService.addUserImage(
                image,
                userService.findUserById(userId),
                PermissionType.PRIVATE,
                CompressionType.NO_COMPRESSION);

        return new AccountUserImageWeb(
                userImage.getId(),
                null,
                userImage.getCreatedDate().getTime());
    }

    @DeleteMapping(value = "/{userId}/images/{userImageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@PathVariable("userId") Long userId,
                                @PathVariable(value = "userImageId") Long userImageId) {
        log.debug("/images/{id}");
        userImageService.deleteUserImage(userService.findUserById(userId), userImageId);
    }

}