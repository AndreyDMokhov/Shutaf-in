package com.shutafin.controller;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.ReadMessagesRequest;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatMessageResponse;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.web.user.UserBaseResponse;
import com.shutafin.processors.annotations.authentication.AuthenticatedUser;
import com.shutafin.processors.annotations.authentication.WebSocketAuthentication;
import com.shutafin.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatAuthorizationService chatAuthorizationService;

    @Autowired
    private ChatManagementService chatManagementService;

    @Autowired
    private ChatInfoService chatInfoService;
    //TODO moved to matching service
    @Autowired
    private UserMatchService userMatchService;

    @Autowired
    private UserSearchService userSearchService;

    @Autowired
    private SessionManagementService sessionManagementService;

    @GetMapping(value = "/new/{chat_title}/{user_id}")
    public ChatWithUsersListDTO addChat(@PathVariable("chat_title") String chatTitle,
                                        @PathVariable("user_id") Long chatMemberUserId,
                                        @AuthenticatedUser Long chatOwner) {

        return chatManagementService.createNewChat(chatTitle, chatOwner, chatMemberUserId);
    }

    @GetMapping(value = "/rename/{chat_id}/{chat_title}")
    public ChatWithUsersListDTO renameChat(@PathVariable("chat_id") Long chatId,
                           @PathVariable("chat_title") String chatTitle,
                           @AuthenticatedUser Long userId) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, userId);
        return chatManagementService.renameChat(chat, chatTitle, userId);
    }

    @GetMapping(value = "/{chat_id}/add/user/{user_id}")
    public void addChatUser(@PathVariable("chat_id") Long chatId,
                            @PathVariable("user_id") Long userId,
                            @AuthenticatedUser Long authenticatedUserId) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, authenticatedUserId);
        chatManagementService.addChatUserToChat(chat, userId);
    }

    @GetMapping(value = "/{chat_id}/remove/user/{user_id}")
    public void removeChatUser(@PathVariable("chat_id") Long chatId,
                               @PathVariable("user_id") Long userId,
                               @AuthenticatedUser Long authenticatedUserId) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, authenticatedUserId);
        chatManagementService.removeChatUserFromChat(chat, userId);
    }

    @GetMapping(value = "/{chat_id}/remove/chat")
    public void removeChat(@PathVariable("chat_id") Long chatId,
                           @AuthenticatedUser Long authenticatedUserId) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, authenticatedUserId);
        chatManagementService.removeChatUserFromChat(chat, authenticatedUserId);
    }

    @GetMapping(value = "/get/chats")
    public List<ChatWithUsersListDTO> getChats(@AuthenticatedUser Long authenticatedUserId) {
        return chatInfoService.getListChats(authenticatedUserId);
    }

    @WebSocketAuthentication
    @MessageMapping("/api/chat/{chat_id}/message")
    @SendTo("/api/subscribe/chat/{chat_id}")
    public ChatMessageResponse send(@DestinationVariable("chat_id") Long chatId,
                                    Message<ChatMessageRequest> message) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        sessionManagementService.validate(accessor.getFirstNativeHeader("session_id"));
        Long authenticatedUserId = sessionManagementService.findUserWithValidSession(accessor.getFirstNativeHeader("session_id"));

        ChatUser chatUser = chatAuthorizationService.findAuthorizedChatUser(chatId, authenticatedUserId);
        ChatMessageRequest chatMessageRequest = message.getPayload();
        ChatMessage chatMessage = chatManagementService.saveChatMessage(chatUser, chatMessageRequest);
        return createChatMessageOutputWeb(chatMessage);
    }


    @GetMapping(value = "/{chat_id}/get/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable("chat_id") Long chatId,
                                                 @AuthenticatedUser Long authenticatedUserId) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, authenticatedUserId);
        List<ChatMessage> chatMessages = chatInfoService.getListMessages(chat, authenticatedUserId);
        return createListChatMessageOutputWeb(chatMessages);
    }
    @GetMapping(value = "/allUsers")
    public List<UserBaseResponse> getUsers(@AuthenticatedUser Long authenticatedUserId) {
        return userSearchService.userBaseResponseByList(userMatchService.findMatchingUsers(authenticatedUserId));
    }

    @PutMapping(value = "/updateMessagesAsRead", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateMessagesAsRead(@RequestBody @Valid ReadMessagesRequest messagesIdList,
                                     @AuthenticatedUser Long userId) {
        chatManagementService.updateMessagesAsRead(messagesIdList.getMessageIdList(), userId);
    }

    private List<ChatMessageResponse> createListChatMessageOutputWeb(List<ChatMessage> chatMessages) {
        return chatMessages
                .stream()
                .map(this::createChatMessageOutputWeb)
                .collect(Collectors.toList());
    }

    private ChatMessageResponse createChatMessageOutputWeb(ChatMessage chatMessage) {
        //todo ms-account
        return ChatMessageResponse
                .builder()
                .userId(chatMessage.getUserId())
                .messageId(chatMessage.getId())
                .firstName("")
                .lastName("")
                .createDate(chatMessage.getCreatedDate())
                .message(chatMessage.getMessage())
                .messageType(chatMessage.getMessageType().getId())
                .usersToNotify(chatMessage.getUsersToNotify())
                .build();
    }

}