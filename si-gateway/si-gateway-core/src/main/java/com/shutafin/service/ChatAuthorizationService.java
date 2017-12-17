package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;

/**
 * Created by Rogov on 05.11.2017.
 */
public interface ChatAuthorizationService {
    Chat findAuthorizedChat(Long chatId, Long userId);
    ChatUser findAuthorizedChatUser(Long chatId, Long userId);
}
