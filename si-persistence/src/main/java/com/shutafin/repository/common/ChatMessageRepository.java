package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

public interface ChatMessageRepository extends PersistentDao<ChatMessage> {
    List<ChatMessage> findChatMessagesByChatAndPermittedUser(Chat chat, User user);
    List<ChatMessage> updateMessagesAsRead(List<Long> messagesIdList);
}
