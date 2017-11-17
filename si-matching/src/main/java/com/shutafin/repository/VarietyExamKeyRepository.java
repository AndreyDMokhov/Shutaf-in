package com.shutafin.repository;

import com.shutafin.model.match.VarietyExamKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VarietyExamKeyRepository extends JpaRepository<VarietyExamKey, Long> {
    VarietyExamKey findByUserExamKey(String key);
}
