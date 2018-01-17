package com.shutafin.repository;


import com.shutafin.model.filter.FilterGender;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public interface FilterGenderRepository extends BaseJpaRepository<FilterGender, Long>{

    @Query("SELECT fg.gender.id FROM FilterGender fg WHERE fg.user.id = :userId")
    Integer findGenderIdByUserId(@Param("userId") Long userId);

    void deleteByUserId(Long userId);

    List<FilterGender> findAllByUserIdIn(List<Long> userIds);
}
