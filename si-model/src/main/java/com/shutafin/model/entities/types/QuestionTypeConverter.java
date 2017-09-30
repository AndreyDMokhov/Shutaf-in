package com.shutafin.model.entities.types;

import javax.persistence.AttributeConverter;

/**
 * Created by evgeny on 9/30/2017.
 */
public class QuestionTypeConverter implements AttributeConverter<QuestionType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(QuestionType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public QuestionType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : QuestionType.getById(dbData);
    }
}
