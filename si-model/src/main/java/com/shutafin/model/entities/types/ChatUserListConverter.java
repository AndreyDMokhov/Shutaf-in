package com.shutafin.model.entities.types;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.persistence.AttributeConverter;
import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * Created by Rogov on 18.10.2017.
 */
public class ChatUserListConverter implements AttributeConverter<LinkedList<Long>, String> {
    @Override
    public String convertToDatabaseColumn(LinkedList<Long> attribute) {
        return attribute == null ? null : new Gson().toJson(attribute);
    }

    @Override
    public LinkedList<Long> convertToEntityAttribute(String dbData) {
        Type collectionType = new TypeToken<LinkedList<Long>>(){}.getType();
        return dbData == null ? null : new Gson().fromJson(dbData, collectionType);
    }
}
