package com.shutafin.repository;

import com.shutafin.model.entities.FilterCity;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 9/13/2017.
 */
public interface FilterCityRepository extends BaseJpaRepository<FilterCity, Long>{

    @Query("SELECT fc.cityId FROM FilterCity fc WHERE fc.userId = :userId ")
    List<Integer> findAllCityIdsByUserId (@Param("userId") Long userId);

    @Query("SELECT fc.userId FROM FilterCity fc WHERE fc.userId in (:filteredUsers) AND fc.cityId in (:cityIds)")
    List<Long> filterUsersFromListByCity(@Param("filteredUsers") List<Long> filteredUsers, @Param("cityId") List<Integer> cityIds);

    void deleteByUserId(Long userId);
}
