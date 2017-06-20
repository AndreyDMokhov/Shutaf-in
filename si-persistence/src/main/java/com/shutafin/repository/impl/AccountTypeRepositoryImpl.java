package com.shutafin.repository.impl;

import com.shutafin.model.entities.infrastructure.AccountType;
import com.shutafin.repository.infrastructure.AccountTypeRepository;
import com.shutafin.repository.base.AbstractConstEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class AccountTypeRepositoryImpl extends AbstractConstEntityDao<AccountType> implements AccountTypeRepository {
}
