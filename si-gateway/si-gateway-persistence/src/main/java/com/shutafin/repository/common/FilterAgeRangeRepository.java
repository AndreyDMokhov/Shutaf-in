package com.shutafin.repository.common;

import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 10/1/2017.
 */
public interface FilterAgeRangeRepository extends BaseJpaRepository<FilterAgeRange, Long>, FilterAgeRangeRepositoryCustom {

    void deleteByUser(User user);
}
