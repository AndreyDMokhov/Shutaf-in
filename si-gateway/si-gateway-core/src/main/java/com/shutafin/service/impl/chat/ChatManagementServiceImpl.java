package com.shutafin.service.impl.chat;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatRepository;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.ChatManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserAccountControllerSender userAccountControllerSender;


    @Override
    @Transactional
    public ChatWithUsersListDTO createNewChat(String chatTitle, Long chatOwnerId, Long chatMemberUserId) {
        Chat chat = new Chat();
        chat.setChatTitle(chatTitle);
        if (chatTitle == null || chatTitle.equals("null")) {
            chat.setHasNoTitle(true);
        }
        chat.setChatTitle(chatTitle);
        chatRepository.save(chat);
        addChatUserToChat(chat, chatOwnerId);
        addChatUserToChat(chat, chatMemberUserId);

        return new ChatWithUsersListDTO(chat.getId(),
                chat.getChatTitle(),
                chat.getHasNoTitle(),
                chatUserRepository.findAllByUserId (chat.getId(), chatOwnerId));
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
        chatMessage.setChatUser(chatUser);
        chatMessage.setMessage(message.getMessage());
        ChatMessageType chatMessageType = ChatMessageType.getById(message.getMessageType());
        if (chatMessageType == null) {
            chatMessageType = ChatMessageType.TEXT;
        }
        chatMessage.setMessageType(chatMessageType);

        List<Long> permittedUsersIdList = getPermittedUsers(chatUser.getChat());
        List<Long> usersToNotifyIdList = getUsersToNotify(permittedUsersIdList, chatUser.getUserId());
        chatMessage.setPermittedUsers(permittedUsersIdList);
        chatMessage.setUsersToNotify(usersToNotifyIdList);
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public void updateMessagesAsRead(List<Long> messagesIdList, Long userId) {
        List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesByMessageIdList(messagesIdList);
        for (ChatMessage chatMessage : chatMessages) {
            List<Long> usersToNotifyIdList = getUsersToNotify(chatMessage.getUsersToNotify(), userId);
            chatMessage.setUsersToNotify(usersToNotifyIdList);
            chatMessageRepository.save(chatMessage);
        }
    }

    @Override
    public ChatWithUsersListDTO renameChat(Chat chat, String chatTitle, Long userId) {
        chat.setChatTitle(chatTitle);
        chat.setHasNoTitle(false);
        chatRepository.save(chat);
        ChatWithUsersListDTO chatWithUsersListDTO = new ChatWithUsersListDTO(chat.getId(), chat.getChatTitle(), chat.getHasNoTitle());

        List<AccountUserWeb> users = chatUserRepository.findAllByUserId(chat.getId(), userId);
        chatWithUsersListDTO.setUsersInChat(users);

        return chatWithUsersListDTO;

    }

    private List<Long> getPermittedUsers(Chat chat) {
        List<ChatUser> chatUsers = chatUserRepository.findChatUsersByChatAndIsActiveUserTrue(chat);
        return chatUsers
                .stream()
                .map(ChatUser::getUserId)
                .collect(Collectors.toList());
    }

    private ChatUser createChatUserAndSetIsActiveTrue(Chat chat, Long userId) {
        ChatUser chatUser = new ChatUser();
        chatUser.setChat(chat);
        chatUser.setUserId(userId);
        AccountUserWeb accountUserWeb = userAccountControllerSender.getBaseUserInfo(userId);
        chatUser.setFirstName(accountUserWeb.getFirstName());
        chatUser.setLastName(accountUserWeb.getLastName());
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
