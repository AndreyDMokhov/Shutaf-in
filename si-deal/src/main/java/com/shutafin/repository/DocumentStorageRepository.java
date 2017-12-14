package com.shutafin.repository;

import com.shutafin.model.entities.DealDocument;
import com.shutafin.model.entities.DocumentStorage;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentStorageRepository extends BaseJpaRepository<DocumentStorage, Long> {

    DocumentStorage findByDealDocument(DealDocument dealDocument);
}
