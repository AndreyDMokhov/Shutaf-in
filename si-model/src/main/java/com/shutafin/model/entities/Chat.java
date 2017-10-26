package com.shutafin.model.entities;

import com.shutafin.model.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "CHAT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chat extends AbstractEntity {

    @Column(name = "CHAT_TITLE", length = 50)
    private String chatTitle;
    @Column(name = "IS_NO_TITLE", nullable = false)
    private Boolean isNoTitle;

}