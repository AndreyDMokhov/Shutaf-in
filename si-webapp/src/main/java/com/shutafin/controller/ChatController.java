package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.ChatManagementService;
import com.shutafin.service.SessionManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class ChatController {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatManagementService chatManagementService;

    @RequestMapping(value = "new/chat", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addChat(HttpServletRequest request, HttpServletResponse response){
        String sessionId = request.getHeader("session_id");
        String userId = request.getHeader("user_id");
        if (StringUtils.isBlank(sessionId) || StringUtils.isBlank(userId)) {
            throw new AuthenticationException();
        }
        User userOwner = sessionManagementService.findUserWithValidSession(sessionId);
        User userPartner = userRepository.findById(userId);
        if (userOwner == null || userPartner == null) {
            throw new AuthenticationException();
        }
        response.setHeader("chat_id", chatManagementService.generateNewChat(userOwner, userPartner));
    }
}