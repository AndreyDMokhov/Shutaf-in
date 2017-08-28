package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.QuestionExtended;
import com.shutafin.model.web.initialization.QuestionExtendedResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;

public interface QuestionExtendedRepository extends Dao<QuestionExtended> {
    List<QuestionExtended> getAllQuestionsExtended();
    List<QuestionExtendedResponseDTO> getLocaleQuestionExtended(Language language);
}
