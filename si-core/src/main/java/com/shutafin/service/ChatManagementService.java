package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.chat.ChatMessageInputWeb;

import java.util.List;

public interface ChatManagementService {

    Chat getNewChat(String chatTitle);
    void addChatUserToChat(User user, Chat chat);
    void addChatUserToChat(Long userId, Chat chat);
    Chat findAuthorizedChat(Long chatId, User user);
    void removeChatUserFromChat(Long userId, Chat chat);
    List<Chat> getListChats(User user);
    ChatMessage saveChatMessage(Long chatId, ChatMessageInputWeb message, User user);
    List<User> getListUsersByChatId(Chat chat);
    List<ChatMessage> getListMessages(Chat chat, User user);
}
