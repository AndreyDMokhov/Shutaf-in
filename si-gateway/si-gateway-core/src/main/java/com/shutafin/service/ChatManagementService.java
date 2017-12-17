package com.shutafin.service;

import com.shutafin.model.entities.*;
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
    void addChatUserToChat(Chat chat, Long userId);
    void removeChatUserFromChat(Chat chat, Long userId);
    ChatMessage saveChatMessage(ChatUser chatUser, ChatMessageRequest message);
    void updateMessagesAsRead(List<Long> messagesIdList, Long chatOwner);
    ChatWithUsersListDTO renameChat(Chat chat, String chatTitle, Long chatOwner);
    }
