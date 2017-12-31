package com.shutafin.service;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.entities.User;

import java.util.List;

/**
 * Created by Rogov on 27.10.2017.
 *
 * Chat "getters"
 */
public interface ChatInfoService {
    List<ChatWithUsersListDTO> getListChats(Long userId);
    List<ChatMessage> getListMessages(Chat chat, Long userId);
}
