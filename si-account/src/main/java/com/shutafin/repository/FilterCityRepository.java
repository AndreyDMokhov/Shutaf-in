package com.shutafin.repository;


import com.shutafin.model.filter.FilterCity;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 9/13/2017.
 */
public interface FilterCityRepository extends BaseJpaRepository<FilterCity, Long>{

    @Query("SELECT fc.city.id FROM FilterCity fc WHERE fc.user.id = :userId ")
    List<Integer> findAllCityIdsByUserId(@Param("userId") Long userId);

    @Query("SELECT fc.user.id FROM FilterCity fc WHERE fc.user.id in (:filteredUsers) AND fc.city.id in (:cityIds)")
    List<Long> filterUsersFromListByCity(@Param("filteredUsers") List<Long> filteredUsers, @Param("cityId") List<Integer> cityIds);

    void deleteByUserId(Long userId);
}
