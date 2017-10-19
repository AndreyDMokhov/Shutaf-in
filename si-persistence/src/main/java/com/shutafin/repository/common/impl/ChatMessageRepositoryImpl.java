package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.ChatMessageRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMessageRepositoryImpl extends AbstractEntityDao<ChatMessage> implements ChatMessageRepository {

    @Override
    public List<ChatMessage> findChatMessagesByChatAndPermittedUser(Chat chat, User user) {
        return (List<ChatMessage>) getSession()
                .createQuery("FROM " + getEntityClass().getName() + " e WHERE e.chat = :chat")
                .setParameter("chat", chat)
                .list();
    }

//    @Override
//    public List<ChatMessageResponse> findChatMessagesByChatAndPermittedUser(Chat chat, User user) {
//
//        StringBuilder hql = new StringBuilder()
//                .append(" select ")
//                .append(" new com.shutafin.model.web.chat.ChatMessageResponse ")
//                .append(" ( ")
//                .append(" cm.id as messageId ")
//                .append(" u.id as userId, ")
//                .append(" u.firstName, ")
//                .append(" u.lastName, ")
//                .append(" cm.createdDate as createdDate ")
//                .append(" cm.message as message ")
//                .append(" cm.messageType as messageType")
//                .append(" cm.usersToNotify as usersToNotify")
//                .append(" ) ")
//                .append(" from User u, ")
//                .append(" ChatMessage cm ")
//                .append(" where ")
//                .append(" cm.user = u ")
//                .append(" and ")
//                .append(" cm.chat = :chat ");
//
//        return (List<ChatMessageResponse>) getSession()
//                .createQuery(hql.toString())
//                .setParameter("chat", chat)
//                .list();
//    }

    @Override
    public List<ChatMessage> updateMessagesAsRead(List<Long> messagesIdList) {
        return (List<ChatMessage>) getSession()
                .createQuery("from " + getEntityClass().getName() + " message where message.id in (:ids)")
                .setParameterList("ids", messagesIdList)
                .list();
    }
}
