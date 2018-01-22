package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;

import java.util.List;

/**
 * Created by Rogov on 27.10.2017.
 *
 * Chat modifiers
 */
public interface ChatManagementService {
    ChatWithUsersListDTO createNewChat(String chatTitle, Long chatOwner, Long chatMemberUserId);
    void addChatUserToChat(Long userId, Chat chat, Long userIdToAdd);
    void removeChatUserFromChat(Long authenticatedUserId, Chat chat, Long userId);
    void deleteChat(Long authenticatedUserId, Long chatId);
    ChatMessage saveChatMessage(ChatUser chatUser, ChatMessageRequest message);
    void updateMessagesAsRead(List<Long> messagesIdList, Long chatOwner);
    ChatWithUsersListDTO renameChat(Chat chat, String chatTitle, Long chatOwner);
    }
