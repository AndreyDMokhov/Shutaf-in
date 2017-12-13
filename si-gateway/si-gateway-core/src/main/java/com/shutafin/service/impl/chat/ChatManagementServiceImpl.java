package com.shutafin.service.impl.chat;

import com.shutafin.exception.exceptions.ResourceNotFoundException;
import com.shutafin.model.entities.*;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatUserDTO;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatRepository;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.ChatManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Rogov on 27.10.2017.
 */
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
    public ChatWithUsersListDTO createNewChat(String chatTitle, User chatOwner, Long chatMemberUserId) {
        Chat chat = new Chat();
        chat.setChatTitle(chatTitle);
        if (chatTitle == null || chatTitle.equals("null")) {
            chat.setHasNoTitle(true);
        }
        chat.setChatTitle(chatTitle);
        chatRepository.save(chat);
        addChatUserToChat(chat, chatOwner.getId());
        addChatUserToChat(chat, chatMemberUserId);
        //TODO moved to Account MS
        List<User> users = Arrays.asList(userRepository.findOne(chatMemberUserId));
        return getChatWithUsersListDTO(chat, users);
    }

    private ChatWithUsersListDTO getChatWithUsersListDTO(Chat chat, List<User> users) {
        List<ChatUserDTO> chatUserDTOs = users.stream()
                .map(u->new ChatUserDTO(u.getId(),u.getFirstName(),u.getLastName()))
                .collect(Collectors.toList());
        return new ChatWithUsersListDTO(chat.getId(),chat.getChatTitle(),chat.getHasNoTitle(),chatUserDTOs);
    }

    @Override
    @Transactional
    public void addChatUserToChat(Chat chat, Long userId) {
        ChatUser chatUser = chatUserRepository.findChatUserByChatIdAndUserId(chat.getId(), userId);
        if (chatUser == null) {
            chatUser = createChatUserAndSetIsActiveTrue(chat, userId);
            chatUserRepository.save(chatUser);
        } else if (!chatUser.getIsActiveUser()) {
            chatUser = setIsActiveTrue(chatUser);
            chatUserRepository.save(chatUser);
        }

    }

    @Override
    @Transactional
    public void removeChatUserFromChat(Chat chat, Long userId) {
        ChatUser chatUser = chatUserRepository.findChatUserByChatIdAndUserId(chat.getId(), userId);
        chatUser.setIsActiveUser(false);
        chatUserRepository.save(chatUser);
    }

    @Override
    @Transactional
    public ChatMessage saveChatMessage(ChatUser chatUser, ChatMessageRequest message) {
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

        List<Long> permittedUsersIdList = getPermittedUsers(chatUser.getChat());
        List<Long> usersToNotifyIdList = getUsersToNotify(permittedUsersIdList, chatUser.getUser().getId());
        chatMessage.setPermittedUsers(permittedUsersIdList);
        chatMessage.setUsersToNotify(usersToNotifyIdList);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public void updateMessagesAsRead(List<Long> messagesIdList, User user) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByMessageIdList(messagesIdList);
        for (ChatMessage chatMessage : chatMessages) {
            List<Long> usersToNotifyIdList = getUsersToNotify(chatMessage.getUsersToNotify(), user.getId());
            chatMessage.setUsersToNotify(usersToNotifyIdList);
            chatMessageRepository.save(chatMessage);
        }
    }

    @Override
    public ChatWithUsersListDTO renameChat(Chat chat, String chatTitle, User user) {
        chat.setChatTitle(chatTitle);
        chat.setHasNoTitle(false);
        chatRepository.save(chat);
        ChatWithUsersListDTO chatWithUsersListDTO = new ChatWithUsersListDTO(chat.getId(),chat.getChatTitle(),chat.getHasNoTitle());
        chatWithUsersListDTO.setUsersInChat(chatUserRepository.findActiveChatUsersIdByChatId(chat.getId(), user.getId()));
        return chatWithUsersListDTO;

    }

    private List<Long> getPermittedUsers(Chat chat) {
        List<ChatUser> chatUsers = chatUserRepository.findChatUsersByChatAndIsActiveUserTrue(chat);
        return chatUsers
                .stream()
                .map(ChatUser::getUser)
                .map(User::getId)
                .collect(Collectors.toList());
    }

    private User getUserById(Long userId) {
        //TODO moved to Account MS
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new ResourceNotFoundException();
        }
        return user;
    }

    private ChatUser createChatUserAndSetIsActiveTrue(Chat chat, Long userId) {
        User user = getUserById(userId);
        ChatUser chatUser = new ChatUser();
        chatUser.setChat(chat);
        chatUser.setUser(user);
        chatUser = setIsActiveTrue(chatUser);
        return chatUser;
    }

    private ChatUser setIsActiveTrue(ChatUser chatUser) {
        chatUser.setIsActiveUser(true);
        chatUser.setJoinDate(new Date());
        return chatUser;
    }

    private List<Long> getUsersToNotify(List<Long> userIdList, Long userId) {
        return userIdList
                .stream()
                .filter(x -> !x.equals(userId))
                .collect(Collectors.toList());
    }
}
