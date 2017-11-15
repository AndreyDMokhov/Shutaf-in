package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.User;

import java.util.List;

/**
 * Created by Rogov on 27.10.2017.
 *
 * Chat "getters"
 */
public interface ChatInfoService {
    List<Chat> getListChats(User user);
    List<User> getListUsersByChatId(Chat chat, User user);
    List<ChatMessage> getListMessages(Chat chat, User user);
}
