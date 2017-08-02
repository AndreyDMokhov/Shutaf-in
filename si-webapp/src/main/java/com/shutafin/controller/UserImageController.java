package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


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
        return new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime());
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb addUserImage(@RequestHeader(value = "session_id", required = false) String sessionId,
                                     @RequestBody UserImageWeb image) {

        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserImage userImage = userImageService.addUserImage(image, user);

        return new UserImageWeb(userImage.getId(), null, userImage.getCreatedDate().getTime());
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

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserImageWeb> getAllUserImages(@RequestHeader(value = "session_id") String sessionId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        List<UserImageWeb> userImages = new ArrayList<>();
        for (UserImage userImage : userImageService.getAllUserImages(user)) {
            userImages.add(new UserImageWeb(userImage.getId(), userImage.getImageStorage().getImageEncoded(),
                    userImage.getCreatedDate().getTime()));
        }
        return userImages;
    }
}
