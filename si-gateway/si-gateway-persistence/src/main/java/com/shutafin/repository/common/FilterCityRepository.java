package com.shutafin.repository.common;

import com.shutafin.model.entities.FilterCity;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 9/13/2017.
 */
@Deprecated
public interface FilterCityRepository extends BaseJpaRepository<FilterCity, Long>, FilterCityRepositoryCustom {
}
