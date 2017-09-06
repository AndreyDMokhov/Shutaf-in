package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInitializationData;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.UserInitializationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/initialization/user")
@Slf4j
public class UserInitializationController {

    @Autowired
    private UserInitializationService userInitializationService;

    @RequestMapping(value = "/init", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Map<String, UserInitializationData> getUserInitializationData(@AuthenticatedUser User user) {
        return new HashMap<String, UserInitializationData>() {{
            log.debug("/initialization/user/init");
            put("userProfile", userInitializationService.getUserInitializationData(user));
        }};
    }
}



