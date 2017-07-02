package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.repository.base.AbstractConstEntityDao;
import com.shutafin.repository.infrastructure.GenderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GenderRepositoryImpl extends AbstractConstEntityDao<Gender> implements GenderRepository {
}
