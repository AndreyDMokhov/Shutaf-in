package com.shutafin.service.impl.chat;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.ChatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rogov on 27.10.2017.
 *
 *
 * Readonly service
 */
@Service
@Transactional
public class ChatInfoServiceImpl implements ChatInfoService {

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;


    @Autowired
    private UserAccountControllerSender userAccountControllerSender;

    @Override
    @Transactional(readOnly = true)
    public List<ChatWithUsersListDTO> getListChats(Long userId) {
        List<ChatWithUsersListDTO> chats = chatUserRepository.findChatsWithActiveUsers(userId);
        for (ChatWithUsersListDTO chat : chats) {
            List<Long> activeUsers = chatUserRepository.findActiveChatUsersIdByChatId(chat.getId(), userId);
            List<AccountUserWeb> baseUserInfos = userAccountControllerSender.getBaseUserInfos(activeUsers);

            chat.setUsersInChat(baseUserInfos);
        }
        return chats;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getListMessages(Chat chat, Long userId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChat(chat);
        chatMessages = chatMessages
                .stream()
                .filter(x -> x.getPermittedUsers().contains(userId))
                .collect(Collectors.toList());
        return chatMessages;
    }
}
