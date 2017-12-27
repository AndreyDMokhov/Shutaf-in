package com.shutafin.model.entities.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Deprecated
@Converter(autoApply = true)
public class EmailReasonConverter implements AttributeConverter<EmailReason, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EmailReason attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public EmailReason convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : EmailReason.getById(dbData);
    }
}
