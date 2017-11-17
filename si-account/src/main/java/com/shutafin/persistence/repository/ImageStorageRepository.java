package com.shutafin.persistence.repository;

import com.shutafin.model.entities.ImageStorage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageStorageRepository extends CrudRepository<ImageStorage, Long> {
}
