package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.initialization.QuestionResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
public interface QuestionRepository extends Dao<Question> {
    List<QuestionResponseDTO> getLocaleQuestions(Language language);
    List<QuestionResponseDTO> getLocaleActiveQuestions(Language language);
}
