package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswerCity;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;
/**
 * Created by evgeny on 9/13/2017.
 */
public interface UserQuestionAnswerCityRepository extends PersistentDao<UserQuestionAnswerCity> {
    List<City> getUserQuestionAnswer(User user, Question question);
    List<User> getAllMatchedUsers(User user);
    void deleteUserCityAnswers(User user);
}
