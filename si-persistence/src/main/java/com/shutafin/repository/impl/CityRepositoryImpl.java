package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.locale.CityLocalized;
import com.shutafin.repository.base.AbstractLocalizedConstEntityDao;
import com.shutafin.repository.infrastructure.CityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepositoryImpl extends AbstractLocalizedConstEntityDao<CityLocalized> implements CityRepository {
}
