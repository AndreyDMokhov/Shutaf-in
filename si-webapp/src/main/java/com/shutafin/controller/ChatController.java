package com.shutafin.controller;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.chat.ChatMessageInputWeb;
import com.shutafin.model.web.chat.ChatMessageOutputWeb;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.WebSocketAuthentication;
import com.shutafin.service.ChatManagementService;
import com.shutafin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatManagementService chatManagementService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/new/{chat_title}", method = RequestMethod.GET)
    public void addChat(@PathVariable("chat_title") String chatTitle, @AuthenticatedUser User user, HttpServletResponse response) {
        Chat chat = chatManagementService.getNewChat(chatTitle);
        chatManagementService.addChatUserToChat(user, chat);
        response.setHeader("chat_id", String.valueOf(chat.getId()));
    }

    @RequestMapping(value = "/{chat_id}/add/user/{user_id}", method = RequestMethod.GET)
    public void addChatUser(@PathVariable("chat_id") Long chatId, @PathVariable("user_id") Long userId,
                            @AuthenticatedUser User user) {
        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        chatManagementService.addChatUserToChat(userId, chat);
    }

    @RequestMapping(value = "/{chat_id}/remove/user/{user_id}", method = RequestMethod.GET)
    public void removeChatUser(@PathVariable("chat_id") Long chatId, @PathVariable("user_id") Long userId,
                               @AuthenticatedUser User user) {
        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        chatManagementService.removeChatUserFromChat(userId, chat);
    }

    @RequestMapping(value = "/get/chats", method = RequestMethod.GET)
    public List<Chat> getChats(@AuthenticatedUser User user, HttpServletResponse response) {
        response.setHeader("user_id", String.valueOf(user.getId()));
        return chatManagementService.getListChats(user);
    }

    @WebSocketAuthentication
    @MessageMapping("/chat/{chat_id}/message")
    @SendTo("/subscribe/chat/{chat_id}")
    public ChatMessageOutputWeb send(@DestinationVariable("chat_id") Long chatId,
                                     Message<ChatMessageInputWeb> message, @AuthenticatedUser User user) {
        ChatMessageInputWeb chatMessageInputWeb = message.getPayload();
        ChatMessage chatMessage = chatManagementService.saveChatMessage(chatId, chatMessageInputWeb, user);
        return createChatMessageOutputWeb(chatMessage);
    }

    @RequestMapping(value = "/{chat_id}/get/users", method = RequestMethod.GET)
    public List<User> getUsers(@PathVariable("chat_id") Long chatId, @AuthenticatedUser User user) {
        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        return chatManagementService.getListUsersByChatId(chat);
    }

    @RequestMapping(value = "/{chat_id}/get/messages", method = RequestMethod.GET)
    public List<ChatMessageOutputWeb> getMessages(@PathVariable("chat_id") Long chatId, @AuthenticatedUser User user) {
        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        List<ChatMessage> chatMessages = chatManagementService.getListMessages(chat, user);
        return createListChatMessageOutputWeb(chatMessages);
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public List<UserInfoWeb> getUsers(@AuthenticatedUser User user) {
        return userService.findAll();
    }

    private List<ChatMessageOutputWeb> createListChatMessageOutputWeb(List<ChatMessage> chatMessages) {
        return chatMessages
                .stream()
                .map(this::createChatMessageOutputWeb)
                .collect(Collectors.toList());
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

}