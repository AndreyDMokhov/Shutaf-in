package com.shutafin.repository;

import com.shutafin.model.entities.FilterGender;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
public interface FilterGenderRepository extends BaseJpaRepository<FilterGender, Long>{

    @Query("SELECT fg.genderId FROM FilterGender fg WHERE fg.userId = :userId")
    Integer findGenderIdByUserId (@Param("userId") Long userId);

    @Query("SELECT fg.userId FROM FilterGender fg WHERE fg.userId in (:filteredUsers) AND fg.genderId = :genderId")
    List<Long> filterUsersFromListByGender(@Param("filteredUsers") List<Long> filteredUsers, @Param("genderId") Integer genderId);

    void deleteByUserId(Long userId);
}
