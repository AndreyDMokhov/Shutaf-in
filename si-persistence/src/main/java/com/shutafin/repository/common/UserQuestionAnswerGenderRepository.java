package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswerGender;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public interface UserQuestionAnswerGenderRepository extends PersistentDao<UserQuestionAnswerGender> {
    List<Gender> getUserQuestionAnswer(User user, Question question);
    List<User> getAllMatchedUsers(User user);
    void deleteUserGenderAnswers(User user);
}
