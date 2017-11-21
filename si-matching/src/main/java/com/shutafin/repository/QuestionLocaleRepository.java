package com.shutafin.repository;

import com.shutafin.model.entities.locale.QuestionLocale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionLocaleRepository extends JpaRepository<QuestionLocale, Long>, QuestionLocaleRepositoryCustom {

}

