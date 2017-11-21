package com.shutafin.repository;

import com.shutafin.model.entities.Deal;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends BaseJpaRepository<Deal, Long> {
}
