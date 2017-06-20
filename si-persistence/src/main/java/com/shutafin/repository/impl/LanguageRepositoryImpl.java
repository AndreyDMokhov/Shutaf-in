package com.shutafin.repository.impl;

import com.shutafin.model.infrastructure.Language;
import com.shutafin.repository.infrastructure.LanguageRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class LanguageRepositoryImpl extends AbstractConstEntityDao<Language> implements LanguageRepository {
}
