package com.shutafin.service.impl.chat;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.exception.exceptions.AuthenticationException;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.service.ChatAuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Rogov on 05.11.2017.
 */
@Service
@Transactional
public class ChatAuthorizationServiceImpl implements ChatAuthorizationService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Override
    @Transactional(readOnly = true)
    public Chat findAuthorizedChat(Long chatId, Long userId) {
        return findAuthorizedChatUser(chatId, userId).getChat();
    }

    @Override
    @Transactional(readOnly = true)
    public ChatUser findAuthorizedChatUser(Long chatId, Long userId) {
        ChatUser chatUser = chatUserRepository.findActiveChatUserByChatIdAndUserId(chatId, userId);
        if (chatUser == null) {
            throw new AuthenticationException();
        }
        return chatUser;
    }
}
