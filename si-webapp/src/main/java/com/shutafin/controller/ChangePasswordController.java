package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.validation.InputValidationException;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.ChangePasswordWeb;
import com.shutafin.service.ChangePasswordService;
import com.shutafin.service.SessionManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("users/password")
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService changePasswordService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @RequestMapping(value = "change", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void changePassword(@RequestBody @Valid ChangePasswordWeb changePasswordWeb, BindingResult result,
                             HttpServletRequest request){
        String sessionId = request.getHeader("session_id");
        if (sessionId == null){
            throw new AuthenticationException();
        }
        User user = sessionManagementService.findUserWithValidSession(sessionId);

        if (user == null) {
            throw new AuthenticationException();
        }

        if (result.hasErrors()) {
            throw new InputValidationException(result);
        }
        changePasswordService.changePassword(changePasswordWeb, user);
    }
}