package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.UserQuestionAnswerRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 8/10/2017.
 */
@Repository
public class UserQuestionAnswerRepositoryImpl extends AbstractEntityDao<UserQuestionAnswer> implements UserQuestionAnswerRepository {
}
