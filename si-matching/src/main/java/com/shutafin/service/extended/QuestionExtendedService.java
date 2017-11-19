package com.shutafin.service.extended;


import com.shutafin.model.DTO.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.DTO.QuestionImportanceDTO;

import java.util.List;

public interface QuestionExtendedService {

    List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Integer languageId);
    List<QuestionImportanceDTO> getQuestionImportanceList(Integer languageId);

}
