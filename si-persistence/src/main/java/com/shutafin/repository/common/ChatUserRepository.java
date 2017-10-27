package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;

public interface ChatUserRepository extends BaseJpaRepository<ChatUser, Long> {

    ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId);
    ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId);
    void setInactiveChatUserAndExitDate(User newUser, Chat chat);
    List<Chat> findChatsByActiveChatUser(User user);
    List<ChatUser> findActiveChatUsersByChat(Chat chat);
}