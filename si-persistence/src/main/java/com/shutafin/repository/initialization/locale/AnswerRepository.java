package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
public interface AnswerRepository extends Dao<Answer> {
    List<AnswerResponseDTO> getLocaleAnswers(Language language);
}
