package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.QuestionImportance;
import com.shutafin.model.web.initialization.QuestionImportanceDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;


public interface QuestionImportanceRepository extends Dao<QuestionImportance> {
    List<QuestionImportance> getAllQuestionImportance();
    List<QuestionImportanceDTO> getAllQuestionImportanceLocale(Language language);
}
