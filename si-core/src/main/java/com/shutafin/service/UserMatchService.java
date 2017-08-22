package com.shutafin.service;

import com.shutafin.model.entities.User;
import com.shutafin.model.matching.QuestionAnswer;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.model.web.initialization.QuestionResponseDTO;
import com.shutafin.model.web.user.UserQuestionAnswerWeb;

import java.util.List;
import java.util.Map;

/**
 * Created by evgeny on 8/12/2017.
 */
public interface UserMatchService {
    List<User> findPartners(User user);
    void saveQuestionsAnswers(User user, List<UserQuestionAnswerWeb> userQuestionsAnswers);
    Map<QuestionResponseDTO, List<AnswerResponseDTO>> getUserMatchExamTemplate(User user);
}
