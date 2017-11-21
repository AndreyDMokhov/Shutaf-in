package com.shutafin.repository;

import com.shutafin.model.entities.DealFolder;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealFolderRepository extends BaseJpaRepository<DealFolder, Long> {
    List<DealFolder> findAllByDealId(Long dealId);
}
