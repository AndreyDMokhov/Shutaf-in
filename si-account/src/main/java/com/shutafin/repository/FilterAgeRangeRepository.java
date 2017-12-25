package com.shutafin.repository;

import com.shutafin.model.filter.FilterAgeRange;
import com.shutafin.model.web.common.AgeRangeWebDTO;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 10/1/2017.
 */
public interface FilterAgeRangeRepository extends BaseJpaRepository<FilterAgeRange, Long> {

    void deleteByUserId(Long userId);

    List<FilterAgeRange> findAllByUserIdIn(List<Long> filteredUsers);

    @Query("SELECT new com.shutafin.model.web.common.AgeRangeWebDTO(far.fromAge, far.toAge) " +
            "FROM FilterAgeRange far WHERE far.user.id = :userId")
    AgeRangeWebDTO findAgeRangeByUserId(@Param("userId") Long userId);
}
