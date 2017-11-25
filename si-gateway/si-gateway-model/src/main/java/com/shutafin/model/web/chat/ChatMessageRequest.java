package com.shutafin.model.web.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MessageType represents the type of the message, like DOCUMENT, IMAGE or TEXT
 */


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChatMessageRequest {

    private String message;
    private Integer messageType;

}
