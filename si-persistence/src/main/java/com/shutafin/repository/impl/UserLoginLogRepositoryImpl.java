package com.shutafin.repository.impl;

import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.repository.UserLoginLogRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 6/20/2017.
 */
@Repository
public class UserLoginLogRepositoryImpl extends AbstractEntityDao<UserLoginLog> implements UserLoginLogRepository {
}
