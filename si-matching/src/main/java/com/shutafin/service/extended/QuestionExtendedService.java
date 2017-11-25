package com.shutafin.service.extended;


import com.shutafin.model.dto.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.dto.QuestionImportanceDTO;

import java.util.List;

public interface QuestionExtendedService {

    List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Integer languageId);
    List<QuestionImportanceDTO> getQuestionImportanceList(Integer languageId);

}