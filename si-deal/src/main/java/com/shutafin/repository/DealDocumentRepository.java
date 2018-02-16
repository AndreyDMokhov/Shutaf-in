package com.shutafin.repository;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealDocumentRepository extends BaseJpaRepository<DealDocument, Long> {
    List<DealDocument> findAllByDealPanelIdAndIsDeletedFalse(Long dealPanelId);
    List<DealDocument> findAllByDealPanelId(Long dealPanelId);
    List<DealDocument> findAllByDealPanelDealId(Long dealId);
}
