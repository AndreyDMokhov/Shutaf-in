package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ChatUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class ChatUserRepositoryImpl extends AbstractEntityDao<ChatUser> implements ChatUserRepository{

    @Override
    public ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId) {
        return (ChatUser) getSession()
                .createQuery("FROM " + getEntityClass().getName() +
                        " e WHERE e.chat.id = :chatId AND e.user.id = :userId AND e.isActiveUser = TRUE")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Override
    public ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId) {
        return (ChatUser) getSession()
                .createQuery("FROM " + getEntityClass().getName() + " e WHERE e.chat.id = :chatId AND e.user.id = :userId")
                .setParameter("chatId", chatId)
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Override
    public void setInactiveChatUserAndExitDate(User user, Chat chat) {
        getSession()
                .createQuery("UPDATE " + getEntityClass().getName() +
                        " e SET e.isActiveUser = FALSE, e.exitDate = :date WHERE e.chat = :chat AND e.user = :user")
                .setParameter("date", new Date())
                .setParameter("chat", chat)
                .setParameter("user", user)
                .executeUpdate();
    }

    @Override
    public List<Chat> findChatsByActiveChatUser(User user) {
        return (List<Chat>) getSession()
                .createQuery("SELECT e.chat FROM " + getEntityClass().getName() + " e WHERE e.user = :user AND e.isActiveUser = TRUE")
                .setParameter("user", user)
                .list();
    }

    @Override
    public List<ChatUser> findActiveChatUsersByChat(Chat chat) {
        return (List<ChatUser>) getSession()
                .createQuery("FROM " + getEntityClass().getName() + " e WHERE e.chat = :chat AND e.isActiveUser = TRUE")
                .setParameter("chat", chat)
                .list();
    }
}
