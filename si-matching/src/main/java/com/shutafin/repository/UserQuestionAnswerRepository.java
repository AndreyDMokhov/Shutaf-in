package com.shutafin.repository;

import com.shutafin.model.entities.UserQuestionAnswer;
import com.shutafin.repository.base.BaseJpaRepository;

public interface UserQuestionAnswerRepository extends BaseJpaRepository<UserQuestionAnswer, Long>, UserQuestionAnswerRepositoryCustom {
    void deleteAllByUserId(Long userId);

}
