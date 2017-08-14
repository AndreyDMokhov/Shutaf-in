package com.shutafin.controller;

import com.shutafin.model.entities.User;
import com.shutafin.service.SessionManagementService;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by evgeny on 8/12/2017.
 */
@RestController
@RequestMapping("/user/match")
public class UserMatchController {

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private SessionManagementService sessionManagementService;


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<User> getMatchPartners(@RequestHeader(value = "session_id") String sessionId) {
        return userMatchService.findPartners(sessionManagementService.findUserWithValidSession(sessionId));
    }
}
