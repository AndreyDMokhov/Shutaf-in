package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.repository.base.PersistentDao;

/**
 * Created by evgeny on 8/10/2017.
 */
public interface UserQuestionAnswerRepository extends PersistentDao<UserQuestionAnswer> {
    void deleteUserAnswers(User user);
}
