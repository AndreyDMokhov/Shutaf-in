package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 8/10/2017.
 */
@Deprecated
public interface UserQuestionAnswerRepository extends BaseJpaRepository<UserQuestionAnswer, Long> {

    void deleteAllByUser(User user);
}
