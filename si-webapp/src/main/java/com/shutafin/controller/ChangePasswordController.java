package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.web.user.ChangePasswordWeb;
import com.shutafin.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/password")
public class ChangePasswordController {

    private static final String STRING_SESSION_D = "session_id";

    @Autowired
    private ChangePasswordService changePasswordService;

    @RequestMapping(value = "/change", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changePassword(@RequestBody @Valid ChangePasswordWeb changePasswordWeb, BindingResult result,
                             HttpServletRequest request){
        String sessionId = request.getHeader(STRING_SESSION_D);
        if (sessionId == null){
            throw new AuthenticationException();
        }
        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        changePasswordService.changePassword(changePasswordWeb, sessionId);
    }
}