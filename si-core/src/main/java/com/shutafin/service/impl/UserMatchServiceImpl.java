package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.matching.QuestionAnswer;
import com.shutafin.model.web.AnswerResponse;
import com.shutafin.model.web.QuestionResponse;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.model.web.initialization.QuestionResponseDTO;
import com.shutafin.model.web.user.UserQuestionAnswerWeb;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import com.shutafin.repository.initialization.locale.AnswerRepository;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import com.shutafin.service.UserMatchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Transactional
@Slf4j
public class UserMatchServiceImpl implements UserMatchService {

    private static Map<User, Set<QuestionAnswer>> usersQuestionsAnswersMap;

    @Autowired
    private UserQuestionAnswerRepository userQuestionAnswerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> findPartners(User user) {
        List<User> result = new ArrayList<User>();

        if (user == null) {
            return result;
        }

        if (usersQuestionsAnswersMap == null){
            initUsersMatchMap();
        }

        Set<QuestionAnswer> userQuestionAnswersSet = usersQuestionsAnswersMap.get(user);
        Set<Map.Entry<User,Set<QuestionAnswer>>> set = usersQuestionsAnswersMap.entrySet();
        for(Map.Entry<User,Set<QuestionAnswer>> entry : set){
            if (!user.equals(entry.getKey()) && userQuestionAnswersSet!=null && userQuestionAnswersSet.containsAll(entry.getValue())){
                result.add(entry.getKey());
            }
        }

        return result;
    }

    @Override
    @Transactional
    public void saveQuestionsAnswers(User user, List<UserQuestionAnswerWeb> userQuestionsAnswers) {

        if (usersQuestionsAnswersMap == null){
            initUsersMatchMap();
        }

        if (usersQuestionsAnswersMap.get(user) != null) {
            userQuestionAnswerRepository.deleteUserAnswers(user);
            usersQuestionsAnswersMap.get(user).clear();
        }

        for (UserQuestionAnswerWeb questionAnswer : userQuestionsAnswers) {
            Question question = questionRepository.findById(questionAnswer.getQuestionId());
            Answer answer = answerRepository.findById(questionAnswer.getAnswerId());
            userQuestionAnswerRepository.save(new UserQuestionAnswer(user, question, answer));

            updateUsersQuestionsAnswersMap(user, question, answer);
        }
    }

    @Override
    @Transactional
    public List<QuestionResponse> getUserQuestionsAnswers(User user){
        List<QuestionResponse> questionsWebList = new ArrayList<>();
        Language language = userAccountRepository.findUserLanguage(user);

        List<QuestionResponseDTO> questions = questionRepository.getLocaleActiveQuestions(language);
        for (QuestionResponseDTO questionResponseDTO : questions) {
            QuestionResponse questionResponse = convertQuestionDtoToWeb(questionResponseDTO);

            List<AnswerResponseDTO> answers = answerRepository.getQuestionLocaleAnswers(language, questionRepository.findById(questionResponseDTO.getId()));
            questionResponse.setAnswers(convertAnswerDtoListToWebList(answers));

            List<UserQuestionAnswer> currentQuestionSelectedAnswers = userQuestionAnswerRepository.getUserQuestionAnswer(user, questionRepository.findById(questionResponseDTO.getId()));
            questionResponse.setSelectedAnswersIds(getIdsFromAnswers(currentQuestionSelectedAnswers));

            questionsWebList.add(questionResponse);
        }

        return questionsWebList;
    }

    private void updateUsersQuestionsAnswersMap(User user, Question question, Answer answer){
        if (usersQuestionsAnswersMap.get(user) == null){
            usersQuestionsAnswersMap.put(user, new HashSet<QuestionAnswer>());
        }
        usersQuestionsAnswersMap.get(user).add(new QuestionAnswer(question, answer));
    }

    private List<Integer> getIdsFromAnswers(List<UserQuestionAnswer> questionSelectedAnswers){
        List<Integer> ids = new ArrayList<>();
        for (UserQuestionAnswer userQuestionAnswer : questionSelectedAnswers) {
            ids.add(userQuestionAnswer.getAnswer().getId());
        }
        return ids;
    }

    private List<AnswerResponse> convertAnswerDtoListToWebList(List<AnswerResponseDTO> answers){
        List<AnswerResponse> answerResponseList = new ArrayList<>();
        for (AnswerResponseDTO answerResponseDTO : answers) {
            answerResponseList.add(convertAnswerDtoToWeb(answerResponseDTO));
        }
        return answerResponseList;
    }

    private AnswerResponse convertAnswerDtoToWeb(AnswerResponseDTO answerResponseDTO){
        return new AnswerResponse(answerResponseDTO.getId(), answerResponseDTO.getDescription(), answerResponseDTO.getUniversal());
    }

    private QuestionResponse convertQuestionDtoToWeb(QuestionResponseDTO questionResponseDTO){
        return new QuestionResponse(questionResponseDTO.getId(), questionResponseDTO.getDescription(), questionResponseDTO.getActive(),null,null);
    }

    private void initUsersMatchMap(){

        log.info(">>>>>>>>>>>>>>>>>>>initUsersMatchMap>>>>>>>>>>>>>>>>>>>");

        usersQuestionsAnswersMap = new HashMap<>();
        List<UserQuestionAnswer> usersQuestionsAnswers = userQuestionAnswerRepository.findAll();
        for (int i = 0 ; i < usersQuestionsAnswers.size(); i++){
            User user = usersQuestionsAnswers.get(i).getUser();
            if (usersQuestionsAnswersMap.get(user) == null){
                usersQuestionsAnswersMap.put(user, new HashSet<QuestionAnswer>());
            }
            Question question = usersQuestionsAnswers.get(i).getQuestion();
            Answer answer = usersQuestionsAnswers.get(i).getAnswer();
            usersQuestionsAnswersMap.get(user).add(new QuestionAnswer(question, answer));
        }
    }
}
