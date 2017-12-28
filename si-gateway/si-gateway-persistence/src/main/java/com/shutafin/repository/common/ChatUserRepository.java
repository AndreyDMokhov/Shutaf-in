package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatUserRepository extends BaseJpaRepository<ChatUser, Long> {

    ChatUser findActiveChatUserByChatIdAndUserId(Long chatId, Long userId);
    ChatUser findChatUserByChatIdAndUserId(Long chatId, Long userId);

    @Query("SELECT new com.shutafin.model.web.account.AccountUserWeb" +
            " (cu.userId, cu.lastName, cu.firstName) " +
            " from ChatUser cu where cu.chat.id = :chatId  AND cu.userId <> :currentUser AND cu.isActiveUser = TRUE ")
    List<AccountUserWeb> findOtherUsersInChatByUserId(@Param("chatId") Long chatId, @Param ("currentUser") Long currentUser);

    @Query("select new com.shutafin.model.web.chat.ChatWithUsersListDTO" +
            "(c.chat.id, c.chat.chatTitle, c.chat.hasNoTitle) " +
            "from ChatUser c where c.userId = :userId AND c.isActiveUser = TRUE ")
    List<ChatWithUsersListDTO> findChatsWithActiveUsers(@Param("userId") Long userId);


    List<ChatUser> findChatUsersByChatAndIsActiveUserTrue (Chat chat);

    List<ChatUser> findAllByUserId(Long userId);
}

