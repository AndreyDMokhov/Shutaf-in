package com.shutafin.service.impl;

import com.shutafin.model.web.matching.UserQuestionExtendedAnswersWeb;
import com.shutafin.sender.matching.QuestionExtendedControllerSender;
import com.shutafin.sender.matching.UserMatchingScoreControllerSender;
import com.shutafin.service.UserQuestionExtendedAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserQuestionExtendedAnswerServiceImpl implements UserQuestionExtendedAnswerService {

    private UserMatchingScoreControllerSender userMatchingScoreControllerSender;

    private QuestionExtendedControllerSender questionExtendedControllerSender;

    @Autowired
    public UserQuestionExtendedAnswerServiceImpl(UserMatchingScoreControllerSender userMatchingScoreControllerSender, QuestionExtendedControllerSender questionExtendedControllerSender) {
        this.userMatchingScoreControllerSender = userMatchingScoreControllerSender;
        this.questionExtendedControllerSender = questionExtendedControllerSender;
    }

    @Override
    public void addUserQuestionExtendedAnswers(Long authenticatedUserId,
                                               List<UserQuestionExtendedAnswersWeb> userQuestionExtendedAnswersWebList) {
        userMatchingScoreControllerSender.addUserQuestionExtendedAnswers(authenticatedUserId, userQuestionExtendedAnswersWebList);
    }

    @Override
    public List<UserQuestionExtendedAnswersWeb> getSelectedQuestionExtendedAnswers(Long authenticatedUserId) {
        return questionExtendedControllerSender.getSelectedQuestionExtendedAnswers(authenticatedUserId);
    }


}
