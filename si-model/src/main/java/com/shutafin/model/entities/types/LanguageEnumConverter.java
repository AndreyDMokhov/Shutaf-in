package com.shutafin.model.entities.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.Serializable;

@Converter(autoApply = true)
public class LanguageEnumConverter implements AttributeConverter<LanguageEnum, Integer>, Serializable {

    @Override
    public Integer convertToDatabaseColumn(LanguageEnum attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public LanguageEnum convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : LanguageEnum.getById(dbData);
    }
}
