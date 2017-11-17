package com.shutafin.model.web.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageResponse {

    private Long userId;
    private Long messageId;
    private String firstName;
    private String lastName;
    private Date createDate;
    private String message;
    private Integer messageType;
    private List<Long> usersToNotify;

}
