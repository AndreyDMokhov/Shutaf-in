package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.AccountStatus;
import com.shutafin.repository.infrastructure.AccountStatusRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class AccountStatusRepositoryImpl extends AbstractConstEntityDao<AccountStatus> implements AccountStatusRepository {
}
