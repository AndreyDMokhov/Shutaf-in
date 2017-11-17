package com.shutafin.service;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.initialization.QuestionImportanceDTO;

import java.util.List;

public interface QuestionExtendedService {

    List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Language language);
    List<QuestionImportanceDTO> getQuestionImportanceList(Language language);

}
