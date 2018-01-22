package com.shutafin.service.impl.chat;

import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.service.ChatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public List<ChatWithUsersListDTO> getListChats(Long userId) {
        List<ChatWithUsersListDTO> chats = chatUserRepository.findChats(userId);
        for (ChatWithUsersListDTO chat : chats) {
            if(chat.getIsActiveUser()){
                chat.setUsersInChat(chatUserRepository.findOtherUsersInChatByUserId(chat.getId(), userId));
            }else{
                chat.setUsersInChat(new ArrayList<>());
            }
        }
        return chats;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getListMessages(Long chatId, Long userId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChatId(chatId);
        chatMessages = chatMessages
                .stream()
                .filter(x -> x.getPermittedUsers().contains(userId))
                .collect(Collectors.toList());
        return chatMessages;
    }
}
