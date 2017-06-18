package com.shutafin.repository.impl;

import com.shutafin.model.common.Gender;
import com.shutafin.repository.GenderRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;


@Repository
public class GenderRepositoryImpl extends AbstractConstEntityDao<Gender> implements GenderRepository {
}
