package com.shutafin.service.impl.chat;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatUserRepository;
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


    @Override
    @Transactional(readOnly = true)
    public List<Chat> getListChats(User user) {
        return chatUserRepository.findChatActiveUsers(user);
    }



    @Override
    @Transactional(readOnly = true)
    public List<User> getListUsersByChatId(Chat chat, User user) {
        List<ChatUser> chatUsers = chatUserRepository.findChatUsersByChatAndIsActiveUserTrue(chat);
        return chatUsers.stream().map(ChatUser::getUser)
                .filter(x -> !x.getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getListMessages(Chat chat, User user) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChat(chat);
        chatMessages = chatMessages
                .stream()
                .filter(x -> x.getPermittedUsers().contains(user.getId()))
                .collect(Collectors.toList());
        return chatMessages;
    }
}
