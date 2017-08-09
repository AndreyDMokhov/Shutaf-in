package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.entities.types.ChatMessageTypeConverter;

import javax.persistence.*;

@Entity
@Table(name = "CHAT_MESSAGE")
public class ChatMessage extends AbstractEntity {

    @JoinColumn(name = "CHAT_ID", nullable = false)
    @ManyToOne
    private Chat chat;

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "MESSAGE", nullable = false)
    @Lob
    private String message;

    @Column(name = "MESSAGE_TYPE_ID", nullable = false)
    @Convert(converter = ChatMessageTypeConverter.class)
    private ChatMessageType messageType;

    @Column(name = "PERMITED_USERS", length = 200)
    private String permitedUsers;

    public ChatMessage() {
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatMessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(ChatMessageType messageType) {
        this.messageType = messageType;
    }

    public String getPermitedUsers() {
        return permitedUsers;
    }

    public void setPermitedUsers(String permitedUsers) {
        this.permitedUsers = permitedUsers;
    }
}
