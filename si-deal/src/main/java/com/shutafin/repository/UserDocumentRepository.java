package com.shutafin.repository;

import com.shutafin.model.entities.UserDocument;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentRepository extends CrudRepository<UserDocument, Long> {
}
