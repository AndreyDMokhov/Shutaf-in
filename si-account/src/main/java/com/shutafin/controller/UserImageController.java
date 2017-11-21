package com.shutafin.controller;

import com.shutafin.core.service.UserImageService;
import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.types.CompressionType;
import com.shutafin.model.types.PermissionType;
import com.shutafin.model.web.user.UserImageWeb;
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

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{userImageId}/users/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb getUserImage(@PathVariable("userId") Long userId,
                                     @PathVariable(value = "userImageId") Long userImageId) {
        log.debug("/images/{id}");
        UserImage image = userImageService.getUserImage(userService.findUserById(userId), userImageId);
        return new UserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @PostMapping(value = "/", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb addUserImage(@RequestParam("userId") Long userId,
                                     @RequestBody @Valid UserImageWeb image, BindingResult result) {
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

        return new UserImageWeb(
                userImage.getId(),
                null,
                userImage.getCreatedDate().getTime());
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(Long userId,
                                @PathVariable(value = "id") Long userImageId) {
        log.debug("/images/{id}");
        userImageService.deleteUserImage(userService.findUserById(userId), userImageId);
    }

}
