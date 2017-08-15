package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ChatMessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMessageRepositoryImpl extends AbstractEntityDao<ChatMessage> implements ChatMessageRepository{

    @Override
    public List<ChatMessage> findChatMessagesByChatAndPermittedUser(Chat chat, User user) {
        return (List<ChatMessage>) getSession()
                .createQuery("FROM " + getEntityClass().getName() + " e WHERE e.chat = :chat AND e.permittedUsers LIKE :userId")
                .setParameter("chat", chat)
                .setParameter("userId", "%,"+user.getId().toString()+",%")
                .list();
    }
}
