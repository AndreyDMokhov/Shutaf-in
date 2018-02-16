package com.shutafin.repository;

import com.shutafin.model.entities.DealSnapshot;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealSnapshotRepository extends BaseJpaRepository<DealSnapshot, Long> {
    List<DealSnapshot> findAllByUserId(Long userId);
}
