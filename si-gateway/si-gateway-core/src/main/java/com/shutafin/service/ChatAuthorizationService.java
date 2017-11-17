package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;

/**
 * Created by Rogov on 05.11.2017.
 */
public interface ChatAuthorizationService {
    Chat findAuthorizedChat(Long chatId, User user);
    ChatUser findAuthorizedChatUser(Long chatId, User user);
}
