package com.shutafin.model.entities.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;
import java.util.List;

/**
 * Created by Rogov on 18.10.2017.
 */
public class ChatUserListConverter implements AttributeConverter<List<Long>, String> {
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    @SneakyThrows
    public String convertToDatabaseColumn(List<Long> attribute) {
        return attribute == null ? null : mapper.writeValueAsString(attribute);
    }


    @Override
    @SneakyThrows
    public List<Long> convertToEntityAttribute(String dbData) {
        return dbData == null ? null : mapper.readValue(dbData, new TypeReference<List<Long>>(){});
    }
}
