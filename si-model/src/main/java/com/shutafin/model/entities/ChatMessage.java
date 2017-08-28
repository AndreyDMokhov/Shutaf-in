package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import com.shutafin.model.entities.types.ChatMessageType;
import com.shutafin.model.entities.types.ChatMessageTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
    private String permittedUsers;

}
