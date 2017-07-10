package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.Language;
import com.shutafin.repository.LanguageRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class LanguageRepositoryImpl extends AbstractConstEntityDao<Language> implements LanguageRepository {
}
