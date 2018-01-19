package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CHAT_USER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatUser extends AbstractEntity {

    @JoinColumn(name = "CHAT_ID", nullable = false)
    @ManyToOne
    private Chat chat;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "IS_ACTIVE_USER", nullable = false)
    private Boolean isActiveUser;

    @Column(name = "IS_DELETED_FROM_CHAT", nullable = false)
    private Boolean isDeletedFromChat;

    @Column(name = "JOIN_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Column(name = "EXIT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;

}
