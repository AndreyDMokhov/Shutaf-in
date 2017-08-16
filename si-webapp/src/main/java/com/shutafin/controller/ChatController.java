package com.shutafin.controller;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.chat.ChatMessageInputWeb;
import com.shutafin.model.web.chat.ChatMessageOutputWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.service.ChatManagementService;
import com.shutafin.service.SessionManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private SessionManagementService sessionManagementService;

    @Autowired
    private ChatManagementService chatManagementService;

    @RequestMapping(value = "/new/{chat_title}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addChat(@PathVariable("chat_title") String chatTitle, @AuthenticatedUser User user, HttpServletResponse response){
        Chat chat = chatManagementService.getNewChat(chatTitle);
        chatManagementService.addChatUserToChat(user, chat);
        response.setHeader("chat_id", String.valueOf(chat.getId()));
        response.setHeader("user_id", String.valueOf(user.getId()));
    }

    @RequestMapping(value = "/{chat_id}/add/user/{user_id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void addChatUser(@PathVariable("chat_id") Long chatId, @PathVariable("user_id") Long userId,
                            @AuthenticatedUser User user){
        Chat chat = getChatByUser(user, chatId);
        chatManagementService.addChatUserToChat(userId, chat);
    }

    @RequestMapping(value = "/{chat_id}/remove/user/{user_id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void removeChatUser(@PathVariable("chat_id") Long chatId, @PathVariable("user_id") Long userId,
                               @AuthenticatedUser User user){
        Chat chat = getChatByUser(user, chatId);
        chatManagementService.removeChatUserFromChat(userId, chat);
    }

    @RequestMapping(value = "/get/chats", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Map<Long, String> getChats(@AuthenticatedUser User user, HttpServletResponse response){
        response.setHeader("user_id", String.valueOf(user.getId()));
        return chatManagementService.getMapChats(user);
    }

    @MessageMapping("/chat/{chat_id}/message")
    @SendTo("/subscribe/chat/{chat_id}")
    public ChatMessageOutputWeb send(@DestinationVariable("chat_id") Long chatId, ChatMessageInputWeb message){
        ChatMessage chatMessage = chatManagementService.saveChatMessage(chatId, message);
        return createChatMessageOutputWeb(chatMessage);
    }

    @RequestMapping(value = "/{chat_id}/get/users", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Map<Long, String> getUsers(@PathVariable("chat_id") Long chatId, @AuthenticatedUser User user){
        Chat chat = getChatByUser(user, chatId);
        return chatManagementService.getMapUsers(chat);
    }

    @RequestMapping(value = "/{chat_id}/get/messages", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public List<ChatMessageOutputWeb> getMessages(@PathVariable("chat_id") Long chatId, @AuthenticatedUser User user){
        Chat chat = getChatByUser(user, chatId);
        List<ChatMessage> chatMessages = chatManagementService.getListMessages(chat, user);
        return createListChatMessageOutputWeb(chatMessages);
    }

    private List<ChatMessageOutputWeb> createListChatMessageOutputWeb(List<ChatMessage> chatMessages) {
        List<ChatMessageOutputWeb> chatMessageOutputWebList = new ArrayList<>();
        for (ChatMessage chatMessage: chatMessages) {
            chatMessageOutputWebList.add(createChatMessageOutputWeb(chatMessage));
        }
        return chatMessageOutputWebList;
    }

    private ChatMessageOutputWeb createChatMessageOutputWeb(ChatMessage chatMessage) {
        ChatMessageOutputWeb chatMessageOutputWeb = new ChatMessageOutputWeb();
        chatMessageOutputWeb.setFirstName(chatMessage.getUser().getFirstName());
        chatMessageOutputWeb.setLastName(chatMessage.getUser().getLastName());
        chatMessageOutputWeb.setCreateDate(chatMessage.getCreatedDate());
        chatMessageOutputWeb.setMessage(chatMessage.getMessage());
        chatMessageOutputWeb.setMessageType(chatMessage.getMessageType().getId());
        return chatMessageOutputWeb;
    }

    private Chat getChatByUser(User user, Long chatId) {
        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        if (chat == null){
            throw new AuthenticationException();
        }
        return chat;
    }

}