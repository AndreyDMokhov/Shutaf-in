package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.QuestionAnswersResponse;
import com.shutafin.model.web.QuestionSelectedAnswersResponse;

import java.util.List;

public interface QuestionRepositoryCustom {

    List<QuestionAnswersResponse> getUserQuestionsAnswers(Language language);
    List<QuestionSelectedAnswersResponse> getUserQuestionsSelectedAnswers(User user);
}
