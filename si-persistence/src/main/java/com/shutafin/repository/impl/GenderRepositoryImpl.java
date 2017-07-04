package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.locale.GenderLocalized;
import com.shutafin.repository.base.AbstractLocalizedConstEntityDao;
import com.shutafin.repository.infrastructure.GenderRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GenderRepositoryImpl extends AbstractLocalizedConstEntityDao<GenderLocalized> implements GenderRepository {
}
