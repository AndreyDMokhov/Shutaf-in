package com.shutafin.repository;

import com.shutafin.model.dto.QuestionsListWithAnswersDTO;

import java.util.List;

public interface QuestionLocaleRepositoryCustom {
    List<QuestionsListWithAnswersDTO> findByLanguageId(Integer languageId);
}
