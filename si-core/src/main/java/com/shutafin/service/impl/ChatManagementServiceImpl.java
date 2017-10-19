package com.shutafin.service.impl;

import com.shutafin.exception.exceptions.AuthenticationException;
import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.AbstractBaseEntity;
import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatRepository;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.ChatManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ChatManagementServiceImpl implements ChatManagementService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Chat getNewChat(String chatTitle) {
        Chat chat = new Chat();
        chat.setChatTitle(chatTitle);
        chatRepository.save(chat);
        return chat;
    }

    @Override
    @Transactional
    public void addChatUserToChat(User user, Chat chat) {
        ChatUser chatUser = chatUserRepository.findChatUserByChatIdAndUserId(chat.getId(), user.getId());
        if (chatUser == null) {
            chatUser = createChatUser(user, chat);
            chatUserRepository.save(chatUser);
        } else if (!chatUser.getIsActiveUser()) {
            chatUser.setIsActiveUser(true);
            chatUser.setJoinDate(new Date());
            chatUserRepository.update(chatUser);
        }
    }

    @Override
    @Transactional
    public void addChatUserToChat(Long userId, Chat chat) {
        User user = getUserById(userId);
        addChatUserToChat(user, chat);
    }

    @Override
    @Transactional(readOnly = true)
    public Chat findAuthorizedChat(Long chatId, User user) {
        ChatUser chatUser = chatUserRepository.findActiveChatUserByChatIdAndUserId(chatId, user.getId());
        Chat chat = chatUser.getChat();
        if (chat == null) {
            throw new AuthenticationException();
        }
        return chat;
    }

    @Override
    @Transactional
    public void removeChatUserFromChat(Long userId, Chat chat) {
        User user = getUserById(userId);
        chatUserRepository.setInactiveChatUserAndExitDate(user, chat);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Chat> getListChats(User user) {
        return chatUserRepository.findChatsByActiveChatUser(user);
    }

    @Override
    @Transactional
    public ChatMessage saveChatMessage(Long chatId, ChatMessageRequest message, User user) {
        ChatUser chatUser = chatUserRepository.findChatUserByChatIdAndUserId(chatId, user.getId());
        if (chatUser == null) {
            throw new AuthenticationException();
        }
        return createChatMessage(chatUser, message);
    }

    private ChatMessage createChatMessage(ChatUser chatUser, ChatMessageRequest message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setChat(chatUser.getChat());
        chatMessage.setUser(chatUser.getUser());
        chatMessage.setMessage(message.getMessage());
        ChatMessageType chatMessageType = ChatMessageType.getById(message.getMessageType());
        if (chatMessageType == null) {
            chatMessageType = ChatMessageType.TEXT;
        }
        chatMessage.setMessageType(chatMessageType);

        LinkedList<Long> permittedUsersIdList = getPermittedUsers(chatUser.getChat());
        LinkedList<Long> usersToNotifyIdList = getUsersToNotify(permittedUsersIdList, chatUser.getUser().getId());
        chatMessage.setPermittedUsers(permittedUsersIdList);
        chatMessage.setUsersToNotify(usersToNotifyIdList);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    private LinkedList<Long> getPermittedUsers(Chat chat) {
        List<ChatUser> chatUsers = chatUserRepository.findActiveChatUsersByChat(chat);
        return chatUsers
                .stream()
                .map(ChatUser::getUser)
                .map(User::getId)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public List<User> getListUsersByChatId(Chat chat, User user) {
        List<ChatUser> chatUsers = chatUserRepository.findActiveChatUsersByChat(chat);
        return chatUsers.stream().map(ChatUser::getUser)
                .filter(x -> !x.getId().equals(user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> getListMessages(Chat chat, User user) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByChatAndPermittedUser(chat, user);
        chatMessages = chatMessages
                .stream()
                .filter(x->x.getPermittedUsers().contains(user.getId()))
                .collect(Collectors.toList());
        return chatMessages;
    }

    @Override
    public void updateMessagesAsRead(List<Long> messagesIdList, User user) {
        List<ChatMessage> chatMessages = chatMessageRepository.updateMessagesAsRead(messagesIdList);
        for (ChatMessage chatMessage : chatMessages) {
            LinkedList<Long> usersToNotifyIdList = getUsersToNotify(chatMessage.getUsersToNotify(), user.getId());
            chatMessage.setUsersToNotify(usersToNotifyIdList);
            chatMessageRepository.update(chatMessage);
        }
    }

    private User getUserById(Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    private ChatUser createChatUser(User user, Chat chat) {
        ChatUser chatUser = new ChatUser();
        chatUser.setChat(chat);
        chatUser.setUser(user);
        chatUser.setIsActiveUser(true);
        chatUser.setJoinDate(new Date());
        return chatUser;
    }

    private LinkedList<Long> getUsersToNotify(LinkedList<Long> userIdList, Long userId) {
        return userIdList
                .stream()
                .filter(x -> !x.equals(userId))
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
