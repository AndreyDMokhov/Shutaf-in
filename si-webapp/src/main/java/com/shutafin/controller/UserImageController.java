package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;


@RestController
@RequestMapping("/images")
public class UserImageController {

    @Autowired
    private UserImageService userImageService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public APIWebResponse getUserImage(@RequestHeader(value = "session_id", required = false) String sessionId,
                                       @PathVariable(value = "id") Long userImageId) {
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }
        UserImage image = userImageService.getUserImage(user, userImageId);
        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(new UserImageWeb(image.getId(), image.getImageStorage().getImageEncoded(),
                image.getCreatedDate().getTime()));
        return apiWebResponse;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public APIWebResponse addUserImage(@RequestHeader(value = "session_id", required = false) String sessionId,
                                     @RequestBody @Valid UserImageWeb image, BindingResult result) {

        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
            throw new AuthenticationException();
        }

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }

        UserImage userImage = userImageService.addUserImage(image, user);
        APIWebResponse apiWebResponse = new APIWebResponse();
        apiWebResponse.setData(new UserImageWeb(userImage.getId(), null, userImage.getCreatedDate().getTime()));
        return apiWebResponse;
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
