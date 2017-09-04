package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/images")
@Slf4j
public class UserImageController {

    @Autowired
    private UserImageService userImageService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb getUserImage(@AuthenticatedUser User user, @PathVariable(value = "id") Long userImageId) {
        log.debug("/images/{id}");
        UserImage image = userImageService.getUserImage(user, userImageId);
        return new UserImageWeb(
                image.getId(),
                image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb addUserImage(@AuthenticatedUser User user,
                                     @RequestBody @Valid UserImageWeb image, BindingResult result) {
        log.debug("/images/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }
        UserImage userImage = userImageService.addUserImage(image, user);
        return new UserImageWeb(
                userImage.getId(),
                null,
                userImage.getCreatedDate().getTime());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@AuthenticatedUser User user,
                                @PathVariable(value = "id") Long userImageId) {
        log.debug("/images/{id}");
        userImageService.deleteUserImage(user, userImageId);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserImageWeb> getAllUserImages(@AuthenticatedUser User user) {
        log.debug("/images/");
        List<UserImage> allUserImages = userImageService.getAllUserImages(user);
        return allUserImages
                .stream()
                .map(x -> new UserImageWeb(
                        x.getId(),
                        x.getImageStorage().getImageEncoded(),
                        x.getCreatedDate().getTime()))
                .collect(Collectors.toList());
    }
}
