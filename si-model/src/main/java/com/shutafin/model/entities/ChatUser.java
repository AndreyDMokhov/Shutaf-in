package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CHAT_USER")
public class ChatUser extends AbstractEntity {

    @JoinColumn(name = "CHAT_ID", nullable = false)
    @ManyToOne
    private Chat chat;

    @JoinColumn(name = "USER_ID", nullable = false)
    @ManyToOne
    private User user;

    @Column(name = "IS_ACTIVE_USER", nullable = false)
    private Boolean isActiveUser;

    @Column(name = "JOIN_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;

    @Column(name = "EXIT_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exitDate;

    public ChatUser() {
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

    public Boolean getActiveUser() {
        return isActiveUser;
    }

    public void setActiveUser(Boolean activeUser) {
        isActiveUser = activeUser;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getExitDate() {
        return exitDate;
    }

    public void setExitDate(Date exitDate) {
        this.exitDate = exitDate;
    }
}
