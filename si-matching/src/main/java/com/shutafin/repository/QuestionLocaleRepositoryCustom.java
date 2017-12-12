package com.shutafin.repository;


import com.shutafin.model.web.matching.QuestionsListWithAnswersDTO;

import java.util.List;

public interface QuestionLocaleRepositoryCustom {
    List<QuestionsListWithAnswersDTO> findByLanguageId(Integer languageId);
}
