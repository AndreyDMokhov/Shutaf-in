package com.shutafin.repository;

import com.shutafin.model.entities.Answer;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends BaseJpaRepository<Answer, Long> {

    @Query("SELECT ans.id from Answer ans where ans.isUniversal = 1")
    List<Integer>  findAllAnswerIdByIsUniversalTrue();
}
