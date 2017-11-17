package com.shutafin.repository;

import com.shutafin.model.entities.locale.AnswerLocale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerLocaleRepository extends JpaRepository<AnswerLocale, Long>, CustomAnswerLocaleRepository {
}
