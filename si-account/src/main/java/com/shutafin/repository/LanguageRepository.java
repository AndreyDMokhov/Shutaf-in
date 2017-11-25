package com.shutafin.repository;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends BaseJpaRepository<Language, Integer> {
}
