package com.shutafin.repository.base;

import com.shutafin.model.entities.infrastructure.Language;

import java.util.List;

public interface LocalizedDao<T> extends Dao<T> {
    List<T> findAllByLanguage(Language language);
}
