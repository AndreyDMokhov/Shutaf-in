package com.shutafin.repository.common;

import com.shutafin.model.entities.Chat;
import com.shutafin.repository.base.BaseJpaRepository;

public interface ChatRepository extends BaseJpaRepository<Chat, Long> {
    Chat findChatById(Long chatId);
}
