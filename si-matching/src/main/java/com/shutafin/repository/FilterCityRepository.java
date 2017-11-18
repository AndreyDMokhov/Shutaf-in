package com.shutafin.repository;

import com.shutafin.model.entities.FilterCity;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 9/13/2017.
 */
public interface FilterCityRepository extends BaseJpaRepository<FilterCity, Long>, FilterCityRepositoryCustom {
}
