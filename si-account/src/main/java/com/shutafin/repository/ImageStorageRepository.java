package com.shutafin.repository;

import com.shutafin.model.entities.ImageStorage;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageStorageRepository extends BaseJpaRepository<ImageStorage, Long> {
}
