package com.shutafin.repository;

import com.shutafin.model.entities.FilterGender;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 9/23/2017.
 */
public interface FilterGenderRepository extends BaseJpaRepository<FilterGender, Long>, FilterGenderRepositoryCustom {
}
