package com.shutafin.service.impl.chat;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
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
    public Chat findAuthorizedChat(Long chatId, User user) {
        return findAuthorizedChatUser(chatId, user).getChat();
    }

    @Override
    @Transactional(readOnly = true)
    public ChatUser findAuthorizedChatUser(Long chatId, User user) {
        ChatUser chatUser = chatUserRepository.findActiveChatUserByChatIdAndUserId(chatId, user.getId());
        if (chatUser == null) {
            throw new AuthenticationException();
        }
        return chatUser;
    }
}
