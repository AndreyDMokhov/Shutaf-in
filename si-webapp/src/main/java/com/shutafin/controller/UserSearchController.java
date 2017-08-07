package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.APIWebResponse;
import com.shutafin.model.web.user.RegistrationRequestWeb;
import com.shutafin.model.web.user.UserImageWeb;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.model.web.user.UserSearchWeb;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInitializationService;
import com.shutafin.service.UserSearchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserSearchController {

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/search/{fullName}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<UserSearchWeb> userSearch(HttpServletRequest request, @PathVariable String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return null;
        }
        String sessionId = request.getHeader("session_id");
       if (StringUtils.isBlank(sessionId)) {
 //           throw new AuthenticationException();
        }
        User user = sessionManagementService.findUserWithValidSession(sessionId);
        if (user == null) {
//            throw new AuthenticationException();
        }

       return userSearchService.userSearch(fullName);
    }
}

