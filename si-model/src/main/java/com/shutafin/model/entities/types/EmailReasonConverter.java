package com.shutafin.model.entities.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Converter(autoApply = true)
public class EmailReasonConverter implements AttributeConverter<EmailReason, Integer> {

    @Override
    public Integer convertToDatabaseColumn(EmailReason attribute) {
        return attribute != null ? attribute.getId() : null;
    }

    @Override
    public EmailReason convertToEntityAttribute(Integer dbData) {
        return dbData != null ? EmailReason.getById(dbData) : null;
    }
}
