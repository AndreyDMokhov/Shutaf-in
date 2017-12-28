package com.shutafin.service.impl.chat;

import com.shutafin.model.entities.Chat;
import com.shutafin.model.entities.ChatMessage;
import com.shutafin.model.entities.ChatUser;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.web.account.AccountUserWeb;
import com.shutafin.model.web.chat.ChatMessageRequest;
import com.shutafin.model.web.chat.ChatWithUsersListDTO;
import com.shutafin.model.web.notification.ChatNotificationWeb;
import com.shutafin.model.web.notification.NotificationReason;
import com.shutafin.repository.common.ChatMessageRepository;
import com.shutafin.repository.common.ChatRepository;
import com.shutafin.repository.common.ChatUserRepository;
import com.shutafin.sender.account.UserAccountControllerSender;
import com.shutafin.service.ChatManagementService;
import com.shutafin.service.WebSocketSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
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

    private static final String NOTIFICATION_DESTINATION = "/api/subscribe/notification";

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatUserRepository chatUserRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserAccountControllerSender userAccountControllerSender;

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @Autowired
    private WebSocketSessionService webSocketSessionService;

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
        addUserToChat(chat, chatOwnerId);
        addUserToChat(chat, chatMemberUserId);

        ChatWithUsersListDTO chatWithUsersListDTO = getChatWithUsersListDTO(chatOwnerId, chat);
        sendNotificationsToUsers(chatWithUsersListDTO.getUsersInChat(), chat, NotificationReason.NEW_CHAT);

        return chatWithUsersListDTO;
    }


    @Override
    @Transactional
    public void addChatUserToChat(Long userId, Chat chat, Long userIdToAdd) {
        addUserToChat(chat, userIdToAdd);
        List<AccountUserWeb> usersToNotify = chatUserRepository.findOtherUsersInChatByUserId(chat.getId(), userId);
        sendNotificationsToUsers(usersToNotify, chat, NotificationReason.ADD_CHAT_USER);
    }

    private void addUserToChat(Chat chat, Long userId) {
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
    public void removeChatUserFromChat(Long authenticatedUserId, Chat chat) {
        setChatUserInActive(authenticatedUserId, chat);

        sendNotificationsToUsers(getChatWithUsersListDTO(authenticatedUserId, chat).getUsersInChat(), chat,
                NotificationReason.REMOVE_CHAT_USER);
    }

    @Override
    @Transactional
    public void removeChatUserFromChat(Long authenticatedUserId, Chat chat, Long userId) {
        ChatWithUsersListDTO chatWithUsersListDTO = getChatWithUsersListDTO(userId, chat);
        notifyChatUser(userId, new ChatNotificationWeb(chatWithUsersListDTO, NotificationReason.REMOVE_CHAT));

        setChatUserInActive(userId, chat);

        sendNotificationsToUsers(chatWithUsersListDTO.getUsersInChat(), chat, NotificationReason.REMOVE_CHAT_USER);
        webSocketSessionService.closeWsSession(userId);
    }

    private void setChatUserInActive(Long authenticatedUserId, Chat chat) {
        ChatUser chatUser = chatUserRepository.findChatUserByChatIdAndUserId(chat.getId(), authenticatedUserId);
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

        ChatWithUsersListDTO chatWithUsersListDTO = getChatWithUsersListDTO(userId, chat);
        sendNotificationsToUsers(chatWithUsersListDTO.getUsersInChat(), chat, NotificationReason.RENAME_CHAT);

        return chatWithUsersListDTO;
    }

    private void sendNotificationsToUsers(List<AccountUserWeb> userIds, Chat chat, NotificationReason notificationReason) {
        for (AccountUserWeb user : userIds) {
            ChatNotificationWeb chatNotificationWeb = new ChatNotificationWeb(getChatWithUsersListDTO(user.getUserId(), chat), notificationReason);
            notifyChatUser(user.getUserId(), chatNotificationWeb);
        }
    }

    private ChatWithUsersListDTO getChatWithUsersListDTO(Long userId, Chat chat) {
        return new ChatWithUsersListDTO(chat.getId(),
                chat.getChatTitle(),
                chat.getHasNoTitle(),
                chatUserRepository.findOtherUsersInChatByUserId(chat.getId(), userId));
    }

    private void notifyChatUser(Long userId, ChatNotificationWeb chatNotificationWeb) {
        String wsSession = webSocketSessionService.findWsSessionId(userId);
        if(wsSession != null){
            messagingTemplate.convertAndSendToUser(wsSession, NOTIFICATION_DESTINATION,
                    chatNotificationWeb,
                    createHeaders(wsSession));
        }
    }

    private MessageHeaders createHeaders(String sessionId) {
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
        headerAccessor.setSessionId(sessionId);
        headerAccessor.setLeaveMutable(true);
        return headerAccessor.getMessageHeaders();
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
