package com.shutafin.controller;

import com.shutafin.model.web.user.ChangePasswordWeb;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("user/account")
public class ChangeEmailController {

    private static final String STRING_SESSION_D = "session_id";

    @RequestMapping(value = "/change-email-request", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void emailChangeRequest(HttpServletRequest request){
        String sessionId = request.getHeader(STRING_SESSION_D);
        System.out.print(sessionId);
    }

    /*@RequestMapping(value = "/change-email-confirm/{link}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void emailChangeConfirmation(HttpServletRequest request, @PathVariable String link){
        String sessionId = request.getHeader(STRING_SESSION_D);
        System.out.print(sessionId);
    }*/

}
