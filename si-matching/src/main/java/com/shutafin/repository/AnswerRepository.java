package com.shutafin.repository;

import com.shutafin.model.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    @Query("SELECT ans.id from Answer ans where ans.isUniversal = 1")
    List<Integer>  findAllAnswerIdByIsUniversalTrue();
}
