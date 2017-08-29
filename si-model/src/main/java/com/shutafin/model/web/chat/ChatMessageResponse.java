package com.shutafin.model.web.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageResponse {

    private String firstName;
    private String lastName;
    private Date createDate;
    private String message;
    private Integer messageType;

}
