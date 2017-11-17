package com.shutafin.repository;

import com.shutafin.model.infrastructure.AnswerElement;

import java.util.List;

public interface CustomAnswerLocaleRepository {
    List<AnswerElement> findAllByLanguageId(Integer languageId);
}
