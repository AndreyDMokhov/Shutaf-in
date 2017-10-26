package com.shutafin.controller;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.MessageIdListWrapper;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatMessageResponse;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.WebSocketAuthentication;
import com.shutafin.service.ChatManagementService;
import com.shutafin.service.UserMatchService;
import com.shutafin.service.UserSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatManagementService chatManagementService;

    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserSearchService userSearchService;

    @RequestMapping(value = "/new/{chat_title}/{user_id}", method = RequestMethod.GET)
    public Chat addChat(@PathVariable("chat_title") String chatTitle,
                        @PathVariable("user_id") Long userId,
                        @AuthenticatedUser User user) {

        Chat chat = chatManagementService.getNewChat(chatTitle, userId);
        chatManagementService.addChatUserToChat(user, chat);
        return chat;
    }

    @RequestMapping(value = "/rename/{chat_id}/{chat_title}", method = RequestMethod.GET)
    public Chat renameChat(@PathVariable("chat_id") Long chatId,
                           @PathVariable("chat_title") String chatTitle,
                           @AuthenticatedUser User user) {

        Chat chat = chatManagementService.renameChat(chatId, chatTitle);
        return chat;
    }

    @RequestMapping(value = "/{chat_id}/add/user/{user_id}", method = RequestMethod.GET)
    public void addChatUser(@PathVariable("chat_id") Long chatId,
                            @PathVariable("user_id") Long userId,
                            @AuthenticatedUser User user) {

        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        chatManagementService.addChatUserToChat(userId, chat);
    }

    @RequestMapping(value = "/{chat_id}/remove/user/{user_id}", method = RequestMethod.GET)
    public void removeChatUser(@PathVariable("chat_id") Long chatId,
                               @PathVariable("user_id") Long userId,
                               @AuthenticatedUser User user) {

        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        chatManagementService.removeChatUserFromChat(userId, chat);
    }

    @RequestMapping(value = "/{chat_id}/remove/chat", method = RequestMethod.GET)
    public void removeChat(@PathVariable("chat_id") Long chatId,
                           @AuthenticatedUser User user) {

        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        chatManagementService.removeChatUserFromChat(user.getId(), chat);
    }

    @RequestMapping(value = "/get/chats", method = RequestMethod.GET)
    public List<Chat> getChats(@AuthenticatedUser User user) {
        return chatManagementService.getListChats(user);
    }

    @WebSocketAuthentication
    @MessageMapping("/api/chat/{chat_id}/message")
    @SendTo("/api/subscribe/chat/{chat_id}")
    public ChatMessageResponse send(@DestinationVariable("chat_id") Long chatId,
                                    Message<ChatMessageRequest> message,
                                    @AuthenticatedUser User user) {

        ChatMessageRequest chatMessageInputWeb = message.getPayload();
        ChatMessage chatMessage = chatManagementService.saveChatMessage(chatId, chatMessageInputWeb, user);
        return createChatMessageOutputWeb(chatMessage);
    }

    @RequestMapping(value = "/{chat_id}/get/users", method = RequestMethod.GET)
    public List<UserBaseResponse> getUsers(@PathVariable("chat_id") Long chatId,
                               @AuthenticatedUser User user) {

        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        return userSearchService.userBaseResponseByList(chatManagementService.getListUsersByChatId(chat, user));
    }

    @RequestMapping(value = "/{chat_id}/get/messages", method = RequestMethod.GET)
    public List<ChatMessageResponse> getMessages(@PathVariable("chat_id") Long chatId,
                                                 @AuthenticatedUser User user) {

        Chat chat = chatManagementService.findAuthorizedChat(chatId, user);
        List<ChatMessage> chatMessages = chatManagementService.getListMessages(chat, user);
        return createListChatMessageOutputWeb(chatMessages);
    }

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public List<UserBaseResponse> getUsers(@AuthenticatedUser User user) {
        return userSearchService.userBaseResponseByList(userMatchService.findMatchingUsers(user));
    }

    @RequestMapping(value = "/updateMessagesAsRead", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateMessagesAsRead (@RequestBody @Valid MessageIdListWrapper messagesIdList, @AuthenticatedUser User user){
        chatManagementService.updateMessagesAsRead(messagesIdList.getMessageIdList(), user);
    }

    private List<ChatMessageResponse> createListChatMessageOutputWeb(List<ChatMessage> chatMessages) {
        return chatMessages
                .stream()
                .map(this::createChatMessageOutputWeb)
                .collect(Collectors.toList());
    }

    private ChatMessageResponse createChatMessageOutputWeb(ChatMessage chatMessage) {
        ChatMessageResponse chatMessageOutputWeb = new ChatMessageResponse();
        chatMessageOutputWeb.setUserId(chatMessage.getUser().getId());
        chatMessageOutputWeb.setMessageId(chatMessage.getId());
        chatMessageOutputWeb.setFirstName(chatMessage.getUser().getFirstName());
        chatMessageOutputWeb.setLastName(chatMessage.getUser().getLastName());
        chatMessageOutputWeb.setCreateDate(chatMessage.getCreatedDate());
        chatMessageOutputWeb.setMessage(chatMessage.getMessage());
        chatMessageOutputWeb.setMessageType(chatMessage.getMessageType().getId());
        chatMessageOutputWeb.setUsersToNotify(chatMessage.getUsersToNotify());
        return chatMessageOutputWeb;
    }

}