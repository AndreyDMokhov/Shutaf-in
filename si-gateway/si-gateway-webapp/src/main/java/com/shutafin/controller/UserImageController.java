package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.account.AccountUserImageWeb;
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
    public AccountUserImageWeb getUserImage(@AuthenticatedUser User user, @PathVariable(value = "id") Long userImageId) {
        log.debug("/images/{id}");
        return userImageService.getUserImage(user, userImageId);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public AccountUserImageWeb addUserImage(@AuthenticatedUser User user,
                                            @RequestBody @Valid AccountUserImageWeb image,
                                            BindingResult result) {
        log.debug("/images/");
        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        return userImageService.addUserImage(image, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@AuthenticatedUser User user,
                                @PathVariable(value = "id") Long userImageId) {
        log.debug("/images/{id}");
        userImageService.deleteUserImage(user, userImageId);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<AccountUserImageWeb> getAllUserImages(@AuthenticatedUser User user) {
        log.debug("/images/");
        List<UserImage> allUserImages = userImageService.getAllUserImages(user);
        return allUserImages
                .stream()
                .map(x -> new AccountUserImageWeb(
                        x.getId(),
                        x.getImageStorage().getImageEncoded(),
                        x.getCreatedDate().getTime()))
                .collect(Collectors.toList());
    }
}
