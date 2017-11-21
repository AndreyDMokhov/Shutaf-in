package com.shutafin.repository;

import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;

import java.util.List;

public interface QuestionLocaleRepositoryCustom {
    List<QuestionsListWithAnswersDTO> findByLanguageId(Integer languageId);
}
