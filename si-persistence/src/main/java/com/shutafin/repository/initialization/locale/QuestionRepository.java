package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;
import com.shutafin.repository.base.Dao;

import java.util.List;

/**
 * Created by evgeny on 8/22/2017.
 */
public interface QuestionRepository extends Dao<Question> {
    List<QuestionAnswersResponse> getUserQuestionsAnswers(Language language);
    List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user);
}
