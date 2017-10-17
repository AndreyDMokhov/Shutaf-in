package com.shutafin.service.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.web.initialization.AnswerExtendedResponseDTO;
import com.shutafin.model.web.initialization.QuestionExtendedResponseDTO;
import com.shutafin.model.web.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.web.initialization.QuestionImportanceDTO;
import com.shutafin.repository.initialization.locale.AnswerExtendedRepository;
import com.shutafin.repository.initialization.locale.QuestionExtendedRepository;
import com.shutafin.repository.initialization.locale.QuestionImportanceRepository;
import com.shutafin.service.QuestionExtendedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class QuestionExtendedServiceImpl implements QuestionExtendedService {

    @Autowired
    private QuestionExtendedRepository questionExtendedRepository;

    @Autowired
    private AnswerExtendedRepository answerExtendedRepository;

    @Autowired
    private QuestionImportanceRepository questionImportanceRepository;

    @Override
    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Language language) {
        List<QuestionExtendedWithAnswersLocaleWeb> questionExtendedWithAnswersList = new ArrayList<>();
        for (QuestionExtendedResponseDTO question : questionExtendedRepository.getLocaleQuestionExtended(language)) {
            QuestionExtendedWithAnswersLocaleWeb questionWithAnswers = new QuestionExtendedWithAnswersLocaleWeb();
            questionWithAnswers.setQuestionId(question.getId());
            questionWithAnswers.setQuestionDescription(question.getDescription());
            questionWithAnswers.setAnswers(new HashMap<Integer, String>());
            for (AnswerExtendedResponseDTO answer : answerExtendedRepository.getLocaleAnswerExtended(language, question.getId())) {
                questionWithAnswers.getAnswers().put(answer.getId(), answer.getDescription());
            }
            questionExtendedWithAnswersList.add(questionWithAnswers);
        }
        return questionExtendedWithAnswersList;
    }

    @Override
    public List<QuestionImportanceDTO> getQuestionImportanceList(Language language) {
        return questionImportanceRepository.getAllQuestionImportanceLocale(language);
    }
}
