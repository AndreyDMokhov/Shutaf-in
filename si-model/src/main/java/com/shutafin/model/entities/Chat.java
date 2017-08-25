package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;

import javax.persistence.*;

@Entity
@Table(name = "CHAT")
public class Chat extends AbstractEntity {

    @Column(name = "CHAT_TITLE", length = 50)
    private String chatTitle;

    public Chat() {
    }

    public String getChatTitle() {
        return chatTitle;
    }

    public void setChatTitle(String chatTitle) {
        this.chatTitle = chatTitle;
    }
}