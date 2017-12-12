package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.web.chat.ChatUserDTO;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUserRepository extends BaseJpaRepository<ChatUser, Long> {

    ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId);
    ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId);

    @Query("select new com.shutafin.model.web.chat.ChatWithUsersListDTO" +
            "(c.chat.id, c.chat.chatTitle, c.chat.hasNoTitle) " +
            "from ChatUser c where c.user.id = :userId AND c.isActiveUser = TRUE ")
    List<ChatWithUsersListDTO> findChatsWithActiveUsers(@Param("user") Long userId);

    @Query("select new com.shutafin.model.web.chat.ChatUserDTO " +
            "(c.user.id, c.user.firstName, c.user.lastName) " +
            "from ChatUser c where c.chat.id = :chatId AND c.user.id <> :currentUser AND c.isActiveUser = TRUE")
    List<ChatUserDTO> findActiveChatUsersIdByChatId(@Param ("chatId") Long chatId, @Param ("currentUser") Long currentUser);

    List<ChatUser> findChatUsersByChatAndIsActiveUserTrue (Chat chat);
}

