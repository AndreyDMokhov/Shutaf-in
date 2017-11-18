package com.shutafin.repository.initialization.locale;

import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 8/22/2017.
 */
@Deprecated
public interface QuestionRepository extends BaseJpaRepository<Question, Integer>, QuestionRepositoryCustom {
}
