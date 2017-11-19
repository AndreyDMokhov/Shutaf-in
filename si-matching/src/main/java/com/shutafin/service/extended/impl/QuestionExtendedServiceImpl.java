package com.shutafin.service.extended.impl;


import com.shutafin.model.DTO.AnswerExtendedResponseDTO;
import com.shutafin.model.DTO.QuestionExtendedResponseDTO;
import com.shutafin.model.DTO.QuestionExtendedWithAnswersLocaleWeb;
import com.shutafin.model.DTO.QuestionImportanceDTO;
import com.shutafin.repository.extended.AnswerExtendedRepository;
import com.shutafin.repository.extended.QuestionExtendedRepository;
import com.shutafin.repository.extended.QuestionImportanceRepository;
import com.shutafin.service.extended.QuestionExtendedService;
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
    public List<QuestionExtendedWithAnswersLocaleWeb> getQuestionsExtendedWithAnswers(Integer languageId) {
        List<QuestionExtendedWithAnswersLocaleWeb> questionExtendedWithAnswersList = new ArrayList<>();
        for (QuestionExtendedResponseDTO question : questionExtendedRepository.getLocaleQuestionsExtended(languageId)) {
            QuestionExtendedWithAnswersLocaleWeb questionWithAnswers = new QuestionExtendedWithAnswersLocaleWeb();
            questionWithAnswers.setQuestionId(question.getId());
            questionWithAnswers.setQuestionDescription(question.getDescription());
            questionWithAnswers.setAnswers(new HashMap<Integer, String>());
            for (AnswerExtendedResponseDTO answer : answerExtendedRepository.getLocaleAnswersExtended(languageId, question.getId())) {
                questionWithAnswers.getAnswers().put(answer.getId(), answer.getDescription());
            }
            questionExtendedWithAnswersList.add(questionWithAnswers);
        }
        return questionExtendedWithAnswersList;
    }

    @Override
    public List<QuestionImportanceDTO> getQuestionImportanceList(Integer languageId) {
        return questionImportanceRepository.getAllQuestionImportanceLocale(languageId);
    }
}
