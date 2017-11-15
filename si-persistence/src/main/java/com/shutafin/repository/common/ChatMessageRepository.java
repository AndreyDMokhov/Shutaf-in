package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends BaseJpaRepository<ChatMessage, Long> {
    List<ChatMessage> findChatMessagesByChat(Chat chat);

    @Query("select message from ChatMessage message where message.id in :messagesIdList")
    List<ChatMessage> findChatMessagesByMessageIdList(@Param("messagesIdList") List<Long> messagesIdList);

}
