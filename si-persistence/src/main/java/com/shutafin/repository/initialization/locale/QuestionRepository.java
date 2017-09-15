package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.QuestionSelectedAnswer;
import com.shutafin.repository.base.Dao;

import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
public interface QuestionRepository extends Dao<Question> {
    List<QuestionResponse> getUserQuestionsAnswers(Language language);
    List<QuestionSelectedAnswer> getUserQuestionsSelectedAnswers(User user);
}
