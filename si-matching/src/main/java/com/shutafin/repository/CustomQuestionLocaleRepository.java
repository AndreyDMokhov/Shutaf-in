package com.shutafin.repository;

import com.shutafin.model.DTO.QuestionsListWithAnswersDTO;

import java.util.List;

public interface CustomQuestionLocaleRepository {
    List<QuestionsListWithAnswersDTO> findByLanguageId(Integer languageId);
}
