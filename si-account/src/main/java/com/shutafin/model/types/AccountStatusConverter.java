package com.shutafin.model.types;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Converter(autoApply = true)
public class AccountStatusConverter implements AttributeConverter<AccountStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(AccountStatus attribute) {
        return attribute == null ? null : attribute.getCode();
    }

    @Override
    public AccountStatus convertToEntityAttribute(Integer dbData) {
        return dbData == null ? null : AccountStatus.getById(dbData);
    }
}
