package com.shutafin.model.web.chat;


public class ChatMessageInputWeb {

    private String message;
    private Integer messageType;


    public ChatMessageInputWeb() {
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }
}
