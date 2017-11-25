package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.chat.ChatMessageRequest;

import java.util.List;

/**
 * Created by Rogov on 27.10.2017.
 *
 * Chat modifiers
 */
public interface ChatManagementService {
    Chat createNewChat(String chatTitle, User chatOwner, Long chatMemberUserId);
    void addChatUserToChat(Chat chat, Long userId);
    void removeChatUserFromChat(Chat chat, Long userId);
    ChatMessage saveChatMessage(ChatUser chatUser, ChatMessageRequest message);
    void updateMessagesAsRead(List<Long> messagesIdList, User user);
    Chat renameChat(Chat chat, String chatTitle);
    }
