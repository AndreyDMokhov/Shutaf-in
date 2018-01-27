package com.shutafin.model.types;

import com.shutafin.model.web.deal.DocumentType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class DocumentTypeConverter implements AttributeConverter<DocumentType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DocumentType attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public DocumentType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : DocumentType.getById(dbData);
    }
}
