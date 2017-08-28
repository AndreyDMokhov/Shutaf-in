package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.AnswerExtended;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.AnswerExtendedResponseDTO;
import com.shutafin.repository.base.Dao;

import java.util.List;

public interface AnswerExtendedRepository extends Dao<AnswerExtended> {
    List<AnswerExtended> getAllAnswersExtended();
    List<AnswerExtendedResponseDTO> getLocaleAnswerExtended(Language language, Integer questionId);
}
