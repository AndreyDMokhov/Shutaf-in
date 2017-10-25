package com.shutafin.repository;

import com.shutafin.model.entities.DocumentStorage;
import com.shutafin.model.entities.UserDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentStorageRepository extends CrudRepository<DocumentStorage, Long> {

    DocumentStorage findByUserDocument(UserDocument userDocument);
}
