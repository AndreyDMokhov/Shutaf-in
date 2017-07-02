package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.infrastructure.CityRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CityRepositoryImpl extends AbstractConstEntityDao<City> implements CityRepository {
}
