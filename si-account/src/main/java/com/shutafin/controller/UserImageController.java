package com.shutafin.controller;

import com.shutafin.core.service.UserImageService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
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
        if (image == null) {
            User user = userService.findUserById(userId);
            return AccountUserImageWeb.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
        }
        return new AccountUserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime(),
                image.getUser().getFirstName(),
                image.getUser().getLastName(),
                image.getUser().getId());
    }

    @GetMapping(value = "/{userId}/images", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getUserImageByUserId(@PathVariable("userId") Long userId) {
        log.debug("/images/byId/{id}");
        UserImage image = userImageService.getUserImage(userService.findUserById(userId));
        if (image == null) {
            User user = userService.findUserById(userId);
            return AccountUserImageWeb.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
        }
        return AccountUserImageWeb.builder()
                .id(image.getId())
                .firstName(image.getUser().getFirstName())
                .lastName(image.getUser().getLastName())
                .userId(image.getUser().getId())
                .image(image.getImageStorage().getImageEncoded())
                .createdDate(image.getCreatedDate().getTime())
                .build();
    }

    @GetMapping(value = "/{userId}/images/original", produces = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb getOriginalUserImageByUserId(@PathVariable("userId") Long userId) {
        log.debug("/images/original/{id}");
        UserImage image = userImageService.getOriginalUserImage(userService.findUserById(userId));
        if (image == null) {
            User user = userService.findUserById(userId);
            return AccountUserImageWeb.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
        }
        return AccountUserImageWeb.builder()
                .id(image.getId())
                .firstName(image.getUser().getFirstName())
                .lastName(image.getUser().getLastName())
                .userId(image.getUser().getId())
                .image(image.getImageStorage().getImageEncoded())
                .createdDate(image.getCreatedDate().getTime())
                .build();
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
        if (image == null) {
            User user = userService.findUserById(userId);
            return AccountUserImageWeb.builder()
                    .userId(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .build();
        }
        return new AccountUserImageWeb(
                userImage.getId(),
                null,
                userImage.getCreatedDate().getTime(),
                userImage.getUser().getFirstName(),
                userImage.getUser().getLastName(),
                userImage.getUser().getId());
    }

    @DeleteMapping(value = "/{userId}/images/{userImageId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@PathVariable("userId") Long userId,
                                @PathVariable(value = "userImageId") Long userImageId) {
        log.debug("/images/{id}");
        userImageService.deleteUserImage(userService.findUserById(userId), userImageId);
    }

}