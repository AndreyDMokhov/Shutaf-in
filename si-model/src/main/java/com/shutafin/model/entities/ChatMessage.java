package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.entities.types.ChatMessageTypeConverter;
import com.shutafin.model.entities.types.ChatUserListConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;

@Entity
@Table(name = "CHAT_MESSAGE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @Column(name = "PERMITTED_USERS", length = 200)
    @Convert(converter = ChatUserListConverter.class)
    private LinkedList<Long> permittedUsers;

    @Column(name = "USERS_TO_NOTIFY", length = 200)
    @Convert(converter = ChatUserListConverter.class)
    private LinkedList<Long> usersToNotify;

}
