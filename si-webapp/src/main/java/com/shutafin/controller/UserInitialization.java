package com.shutafin.controller;

import com.shutafin.model.web.user.UserInit;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInitialization")
public class UserInitialization {
    @Autowired
    private UserInitializationService userInitializationService;

    @RequestMapping(value = "/init", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, UserInit> getAll(@RequestBody String sessionId) {
        return new HashMap<String, UserInit>() {{
            put("userProfile", userInitializationService.getAuthenticatedUser(sessionId));
        }};
    }
}



