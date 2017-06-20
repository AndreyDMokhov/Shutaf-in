package com.shutafin.repository.impl;

import com.shutafin.model.infrastructure.AccountStatus;
import com.shutafin.repository.infrastructure.AccountStatusRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class AccountStatusRepositoryImpl extends AbstractConstEntityDao<AccountStatus> implements AccountStatusRepository {
}
