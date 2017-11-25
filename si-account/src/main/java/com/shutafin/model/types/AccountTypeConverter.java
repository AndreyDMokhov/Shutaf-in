package com.shutafin.model.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Converter(autoApply = true)
public class AccountTypeConverter implements AttributeConverter<AccountType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountType attribute) {
        return attribute == null ? null : attribute.getId();
    }

    @Override
    public AccountType convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : AccountType.getById(dbData);
    }
}
