package com.shutafin.model.entities.types;

public enum ChatMessageType {
    TEXT(1),
    IMAGE(2);

    private Integer id;

    ChatMessageType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static ChatMessageType getById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Message type ID cannot be null");
        }

        for (ChatMessageType chatMessageType : values()) {
            if (chatMessageType.getId().equals(id)) {
                return chatMessageType;
            }
        }

        throw new IllegalArgumentException("Message type with ID [" + id + "] does not exist");
    }
}
