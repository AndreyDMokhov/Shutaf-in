package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.chat.ChatMessageRequest;

import java.util.List;

public interface ChatManagementService {

    Chat getNewChat(String chatTitle, Long userId);
    void addChatUserToChat(User user, Chat chat);
    void addChatUserToChat(Long userId, Chat chat);
    Chat findAuthorizedChat(Long chatId, User user);
    void removeChatUserFromChat(Long userId, Chat chat);
    List<Chat> getListChats(User user);
    ChatMessage saveChatMessage(Long chatId, ChatMessageRequest message, User user);
    List<User> getListUsersByChatId(Chat chat,User user);
    List<ChatMessage> getListMessages(Chat chat, User user);
    void updateMessagesAsRead(List<Long> messagesIdList, User user);
    Chat renameChat(Long chatId, String chatTitle);
}
