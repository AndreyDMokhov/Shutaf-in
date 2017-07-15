package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Marina on 03/07/2017.
 */
@RestController
@RequestMapping("/account/security-settings")
public class SecuritySettingsController {

    /*@RequestMapping(value = "/getUserData", method = RequestMethod.GET)

    public void getUserData(HttpServletRequest request) {
        String sessionId = request.getHeader("session_id");
        System.out.println(sessionId);
    }*/
    @RequestMapping(value = "/getEmail", method = RequestMethod.GET)
    public void getEmail(@RequestHeader("session_id") String sessionId) {
    }


    @RequestMapping(value = "/email", method = RequestMethod.PUT)
    public void updateEmail(@RequestHeader("session_id") String sessionId) {
    }

    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public void updatePassword(@RequestHeader("session_id") String sessionId) {
    }



}
