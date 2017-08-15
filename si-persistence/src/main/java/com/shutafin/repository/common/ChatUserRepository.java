package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

public interface ChatUserRepository extends PersistentDao<ChatUser>{

    ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId);
    ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId);
    void setInactiveChatUserAndExitDate(User newUser, Chat chat);
    List<Chat> findChatsByActiveChatUser(User user);
    List<ChatUser> findActiveChatUsersByChat(Chat chat);
}
