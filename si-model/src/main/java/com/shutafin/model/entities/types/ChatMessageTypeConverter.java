package com.shutafin.model.entities.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ChatMessageTypeConverter implements AttributeConverter<ChatMessageType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ChatMessageType attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public ChatMessageType convertToEntityAttribute(Integer dbData) {
        return dbData != null ? ChatMessageType.getById(dbData) : null;
    }
}
