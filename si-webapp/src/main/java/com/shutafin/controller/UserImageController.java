package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.ResourceDoesNotExistException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/images")
public class UserImageController {

    @Autowired
    UserImageService userImageService;

    @Autowired
    SessionManagementService sessionManagementService;

    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserImageWeb getUserImage(@RequestHeader(value = "session_id") String sessionId,
                                     @RequestParam(value = "image_id") Long userImageId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserImage image = userImageService.getUserImage(user, userImageId);
        if (image == null) {
            throw new ResourceDoesNotExistException("No User Image found");
        }
        return new UserImageWeb(image.getImageStorage().getImageEncoded(), image.getCreatedDate().toString());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addUserImage(@RequestHeader(value = "session_id") String sessionId,
                               @RequestBody String image) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        image = image.split(":")[1].split("\"")[1];
        userImageService.addUserImage(image, user);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteUserImage(@RequestHeader(value = "session_id") String sessionId,
                               @RequestParam(value = "image_id") Long userImageId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserImage image = userImageService.getUserImage(user, userImageId);
        if (image == null) {
            throw new ResourceDoesNotExistException("No User Image found");
        }
        userImageService.deleteUserImage(image);
    }
}
