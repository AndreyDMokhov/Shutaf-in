package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUserRepository extends BaseJpaRepository<ChatUser, Long> {

    ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId);
    ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId);

    @Query("select new com.shutafin.model.web.chat.ChatWithUsersListDTO" +
            "(c.chat.id, c.chat.chatTitle, c.chat.hasNoTitle) " +
            "from ChatUser c where c.userId = :userId AND c.isActiveUser = TRUE ")
    List<ChatWithUsersListDTO> findChatsWithActiveUsers(@Param("userId") Long userId);

    @Query("select c.userId from ChatUser c where c.chat.id = :chatId AND c.userId <> :currentUser AND c.isActiveUser = TRUE")
    List<Long> findActiveChatUsersIdByChatId(@Param ("chatId") Long chatId, @Param ("currentUser") Long currentUser);

    List<ChatUser> findChatUsersByChatAndIsActiveUserTrue (Chat chat);
}

