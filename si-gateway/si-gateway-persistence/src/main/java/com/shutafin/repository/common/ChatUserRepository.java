package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUserRepository extends BaseJpaRepository<ChatUser, Long> {

    ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId);
    ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId);

    @Query("select e.chat from ChatUser e where e.user = :user AND e.isActiveUser = TRUE")
    List<Chat> findChatActiveUsers(@Param("user") User user);

    List<ChatUser> findChatUsersByChatAndIsActiveUserTrue (Chat chat);
}

