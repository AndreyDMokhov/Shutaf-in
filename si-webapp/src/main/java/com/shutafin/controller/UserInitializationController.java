package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserInitializationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/userInitialization")
public class UserInitializationController {

    @Autowired
    private UserInitializationService userInitializationService;

    @RequestMapping(value = "/init", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, UserInit> getAll(@AuthenticatedUser User user) {


        return new HashMap<String, UserInit>() {{
            put("userProfile", userInitializationService.getUserInitData(user));
        }};
    }
}



