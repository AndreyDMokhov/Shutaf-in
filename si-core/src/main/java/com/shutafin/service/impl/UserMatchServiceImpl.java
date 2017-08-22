package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.model.entities.infrastructure.Answer;
import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.model.matching.QuestionAnswer;
import com.shutafin.model.web.initialization.AnswerResponseDTO;
import com.shutafin.model.web.initialization.QuestionResponseDTO;
import com.shutafin.model.web.user.UserQuestionAnswerWeb;
import com.shutafin.repository.account.UserAccountRepository;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.initialization.locale.AnswerRepository;
import com.shutafin.repository.initialization.locale.QuestionRepository;
import com.shutafin.service.UserMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by evgeny on 8/12/2017.
 */
@Service
@Transactional
public class UserMatchServiceImpl implements UserMatchService {

    private static HashMap<User, Set<QuestionAnswer>> usersQuestionsAnswersMap = null;

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

        if (user == null)
            return result;

        if (usersQuestionsAnswersMap == null){
            initUsersMatchMap();
        }

        Set<QuestionAnswer> userQuestionAnswersSet = usersQuestionsAnswersMap.get(user);
        Set<Map.Entry<User,Set<QuestionAnswer>>> set = usersQuestionsAnswersMap.entrySet();
        for(Map.Entry<User,Set<QuestionAnswer>> entry : set){
            if (!user.equals(entry.getKey())){
                if (userQuestionAnswersSet.containsAll(entry.getValue()))
                    result.add(entry.getKey());
            }
        }

        return result;
    }

    @Override
    @Transactional
    public void saveQuestionsAnswers(User user, List<UserQuestionAnswerWeb> userQuestionsAnswers) {
        for (UserQuestionAnswerWeb questionAnswer : userQuestionsAnswers) {
            Question question = questionRepository.findById(questionAnswer.getQuestionId());
            Answer answer = answerRepository.findById(questionAnswer.getAnswerId());
            userQuestionAnswerRepository.save(new UserQuestionAnswer(user, question, answer));
        }
    }

    @Override
    @Transactional
    public Map<QuestionResponseDTO, List<AnswerResponseDTO>> getUserMatchExamTemplate(User user){
        Map<QuestionResponseDTO, List<AnswerResponseDTO>> template = new HashMap<>();
        Language language = userAccountRepository.findUserLanguage(user);
        List<QuestionResponseDTO> questions = questionRepository.getLocaleQuestions(language);
        for (QuestionResponseDTO questionResponseDTO : questions) {
            List<AnswerResponseDTO> answers = answerRepository.getLocaleAnswers(language);
            template.put(questionResponseDTO, answers);
        }

        return template;
    }

    private void initUsersMatchMap(){
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
