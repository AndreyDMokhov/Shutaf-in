package com.shutafin.controller;

import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;


@RestController
@RequestMapping("/images")
@Slf4j
public class UserImageController {

    @Autowired
    private UserImageService userImageService;


    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public APIWebResponse getUserImage(@AuthenticatedUser User user, @PathVariable(value = "id") Long userImageId) {

        UserImage image = userImageService.getUserImage(user, userImageId);
        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime()));
        return apiWebResponse;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public APIWebResponse addUserImage(@AuthenticatedUser User user,
                             @RequestBody @Valid UserImageWeb image, BindingResult result) {

        if (result.hasErrors()) {
            log.warn("Input validation exception:");
            log.warn(result.toString());
            throw new InputValidationException(result);
        }

        UserImage userImage = userImageService.addUserImage(image, user);
        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(new UserImageWeb(userImage.getId(), null, userImage.getCreatedDate().getTime()));
        return apiWebResponse;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@AuthenticatedUser User user,
                                @PathVariable(value = "id") Long userImageId) {

        userImageService.deleteUserImage(user, userImageId);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserImageWeb> getAllUserImages(@AuthenticatedUser User user) {

        List<UserImageWeb> userImages = new ArrayList<>();
        for (UserImage userImage : userImageService.getAllUserImages(user)) {
            userImages.add(new UserImageWeb(userImage.getId(), userImage.getImageStorage().getImageEncoded(),
                    userImage.getCreatedDate().getTime()));
        }
        return userImages;
    }
}
