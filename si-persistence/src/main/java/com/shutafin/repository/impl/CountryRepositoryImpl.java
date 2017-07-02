package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.Country;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.infrastructure.CountryRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CountryRepositoryImpl extends AbstractConstEntityDao<Country> implements CountryRepository {
}
