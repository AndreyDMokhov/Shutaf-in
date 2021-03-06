package com.shutafin.controller;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.ReadMessagesRequest;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatMessageResponse;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.web.matching.UserBaseResponse;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.OnError;
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

    @Autowired
    private UserMatchService userMatchService;


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
        chatManagementService.addChatUserToChat(authenticatedUserId, chat, userId);
    }

    @GetMapping(value = "/{chat_id}/remove/user/{user_id}")
    public void removeChatUser(@PathVariable("chat_id") Long chatId,
                               @PathVariable("user_id") Long userId,
                               @AuthenticatedUser Long authenticatedUserId) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, authenticatedUserId);
        chatManagementService.removeChatUserFromChat(authenticatedUserId, chat, userId);
    }

    @GetMapping(value = "/{chat_id}/delete/chat")
    public void deleteChat(@PathVariable("chat_id") Long chatId,
                           @AuthenticatedUser Long authenticatedUserId) {

        chatManagementService.deleteChat(authenticatedUserId, chatId);
    }

    @GetMapping(value = "/get/chats")
    public List<ChatWithUsersListDTO> getChats(@AuthenticatedUser Long authenticatedUserId) {
        return chatInfoService.getListChats(authenticatedUserId);
    }

    @WebSocketAuthentication
    @MessageMapping("/api/chat/{chat_id}/message")
    @SendTo("/api/subscribe/chat/{chat_id}")
    public ChatMessageResponse send(@DestinationVariable("chat_id") Long chatId,
                                    Message<ChatMessageRequest> message,
                                    @AuthenticatedUser Long userId) {

        ChatUser chatUser = chatAuthorizationService.findAuthorizedChatUser(chatId, userId);
        ChatMessageRequest chatMessageRequest = message.getPayload();

        ChatMessage chatMessage = chatManagementService.saveChatMessage(chatUser, chatMessageRequest);
        return createChatMessageOutputWeb(chatMessage);
    }


    @GetMapping(value = "/{chat_id}/get/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable("chat_id") Long chatId,
                                                 @AuthenticatedUser Long authenticatedUserId) {

        List<ChatMessage> chatMessages = chatInfoService.getListMessages(chatId, authenticatedUserId);
        return createListChatMessageOutputWeb(chatMessages);
    }

    @GetMapping(value = "/allUsers")
    public List<UserBaseResponse> getUsers(@AuthenticatedUser Long authenticatedUserId,
                                           @RequestParam(value = "fullname", required = false) String fullname) {
        return userMatchService.getMatchedUserBaseResponses(authenticatedUserId, fullname, 0, 10, null);
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

    @OnError
    public void onError(Throwable t) {
        log.warn("WebSocket error: {}", t);
    }

    private ChatMessageResponse createChatMessageOutputWeb(ChatMessage chatMessage) {
        return ChatMessageResponse
                .builder()
                .userId(chatMessage.getChatUser().getUserId())
                .messageId(chatMessage.getId())
                .firstName(chatMessage.getChatUser().getFirstName())
                .lastName(chatMessage.getChatUser().getLastName())
                .createDate(chatMessage.getCreatedDate())
                .message(chatMessage.getMessage())
                .messageType(chatMessage.getMessageType().getId())
                .usersToNotify(chatMessage.getUsersToNotify())
                .build();
    }

}