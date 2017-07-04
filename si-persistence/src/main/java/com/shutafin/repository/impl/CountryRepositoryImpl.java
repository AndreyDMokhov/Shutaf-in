package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.locale.CountryLocalized;
import com.shutafin.repository.base.AbstractLocalizedConstEntityDao;
import com.shutafin.repository.infrastructure.CountryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CountryRepositoryImpl extends AbstractLocalizedConstEntityDao<CountryLocalized> implements CountryRepository {
}
