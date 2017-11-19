package com.shutafin.repository;

import com.shutafin.model.DTO.AgeRangeWebDTO;
import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 10/1/2017.
 */
public interface FilterAgeRangeRepository extends BaseJpaRepository<FilterAgeRange, Long> {

    void deleteByUserId(Long userId);

    @Query("SELECT far.userId FROM FilterAgeRange far WHERE far.userId in (:filteredUsers) " +
            "AND far.fromAge between :fromAge and :toAge AND far.toAge between :fromAge and :toAge")
    List<Long> filterUsersFromListByAge(@Param("filteredUsers") List<Long> filteredUsers,
                                        @Param("fromAge") Integer fromAge,
                                        @Param("toAge") Integer toAge);

    @Query("SELECT new com.shutafin.model.DTO.AgeRangeWebDTO(far.fromAge, far.toAge) " +
            "FROM FilterAgeRange far WHERE far.userId = :userId")
    AgeRangeWebDTO findAgeRangeByUserId(@Param("userId") Long userId);
}
