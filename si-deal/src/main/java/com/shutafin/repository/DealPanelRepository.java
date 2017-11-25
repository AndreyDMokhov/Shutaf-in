package com.shutafin.repository;

import com.shutafin.model.entities.DealPanel;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealPanelRepository extends BaseJpaRepository<DealPanel, Long> {
    List<DealPanel> findAllByDealId(Long dealId);
    List<DealPanel> findAllByDealIdAndIsDeletedFalse(Long dealId);
}
