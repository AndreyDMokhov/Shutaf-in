package com.shutafin.model.entities.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ChatMessageTypeConverter implements AttributeConverter<ChatMessageType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ChatMessageType attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public ChatMessageType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : ChatMessageType.getById(dbData);
    }
}
