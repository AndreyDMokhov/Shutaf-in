package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.UserSession;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInitialization")
public class UserInitializationController {
    @Autowired
    private UserInitializationService userInitializationService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "/init", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, UserInit> getAll(@RequestHeader(value = "session_id") String sessionId) {

        UserSession userSession = sessionManagementService.findSessionBySessionIdAndIiValid(sessionId);

        if (userSession == null) {
            throw new AuthenticationException();
        }
        return new HashMap<String, UserInit>() {{
            put("userProfile", userInitializationService.getUserInitData(userSession.getUser()));
        }};
    }
}



