package com.shutafin.controller;

import com.shutafin.model.entities.*;
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

    @GetMapping(value = "/new/{chat_title}/{user_id}")
    public ChatWithUsersListDTO addChat(@PathVariable("chat_title") String chatTitle,
                                        @PathVariable("user_id") Long chatMemberUserId,
                                        @AuthenticatedUser User chatOwner) {

        return chatManagementService.createNewChat(chatTitle, chatOwner, chatMemberUserId);
    }

    @GetMapping(value = "/rename/{chat_id}/{chat_title}")
    public ChatWithUsersListDTO renameChat(@PathVariable("chat_id") Long chatId,
                           @PathVariable("chat_title") String chatTitle,
                           @AuthenticatedUser User user) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, user);
        return chatManagementService.renameChat(chat, chatTitle, user);
    }

    @GetMapping(value = "/{chat_id}/add/user/{user_id}")
    public void addChatUser(@PathVariable("chat_id") Long chatId,
                            @PathVariable("user_id") Long userId,
                            @AuthenticatedUser User user) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, user);
        chatManagementService.addChatUserToChat(chat, userId);
    }

    @GetMapping(value = "/{chat_id}/remove/user/{user_id}")
    public void removeChatUser(@PathVariable("chat_id") Long chatId,
                               @PathVariable("user_id") Long userId,
                               @AuthenticatedUser User user) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, user);
        chatManagementService.removeChatUserFromChat(chat, userId);
    }

    @GetMapping(value = "/{chat_id}/remove/chat")
    public void removeChat(@PathVariable("chat_id") Long chatId,
                           @AuthenticatedUser User user) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, user);
        chatManagementService.removeChatUserFromChat(chat, user.getId());
    }

    @GetMapping(value = "/get/chats")
    public List<ChatWithUsersListDTO> getChats(@AuthenticatedUser User user) {
        return chatInfoService.getListChats(user.getId());
    }

    @WebSocketAuthentication
    @MessageMapping("/api/chat/{chat_id}/message")
    @SendTo("/api/subscribe/chat/{chat_id}")
    public ChatMessageResponse send(@DestinationVariable("chat_id") Long chatId,
                                    Message<ChatMessageRequest> message,
                                    @AuthenticatedUser User user) {

        ChatUser chatUser = chatAuthorizationService.findAuthorizedChatUser(chatId, user);
        ChatMessageRequest chatMessageRequest = message.getPayload();
        ChatMessage chatMessage = chatManagementService.saveChatMessage(chatUser, chatMessageRequest);
        return createChatMessageOutputWeb(chatMessage);
    }


    @GetMapping(value = "/{chat_id}/get/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable("chat_id") Long chatId,
                                                 @AuthenticatedUser User user) {

        Chat chat = chatAuthorizationService.findAuthorizedChat(chatId, user);
        List<ChatMessage> chatMessages = chatInfoService.getListMessages(chat, user);
        return createListChatMessageOutputWeb(chatMessages);
    }
    @GetMapping(value = "/allUsers")
    public List<UserBaseResponse> getUsers(@AuthenticatedUser User user) {
        return userSearchService.userBaseResponseByList(userMatchService.findMatchingUsers(user));
    }

    @PutMapping(value = "/updateMessagesAsRead", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void updateMessagesAsRead(@RequestBody @Valid ReadMessagesRequest messagesIdList, @AuthenticatedUser User user) {
        chatManagementService.updateMessagesAsRead(messagesIdList.getMessageIdList(), user);
    }

    private List<ChatMessageResponse> createListChatMessageOutputWeb(List<ChatMessage> chatMessages) {
        return chatMessages
                .stream()
                .map(this::createChatMessageOutputWeb)
                .collect(Collectors.toList());
    }

    private ChatMessageResponse createChatMessageOutputWeb(ChatMessage chatMessage) {
        return ChatMessageResponse
                .builder()
                .userId(chatMessage.getUser().getId())
                .messageId(chatMessage.getId())
                .firstName(chatMessage.getUser().getFirstName())
                .lastName(chatMessage.getUser().getLastName())
                .createDate(chatMessage.getCreatedDate())
                .message(chatMessage.getMessage())
                .messageType(chatMessage.getMessageType().getId())
                .usersToNotify(chatMessage.getUsersToNotify())
                .build();
    }

}