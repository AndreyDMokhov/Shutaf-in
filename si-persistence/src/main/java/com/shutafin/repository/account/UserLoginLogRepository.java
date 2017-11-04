package com.shutafin.repository.account;

import com.shutafin.model.entities.UserLoginLog;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 6/20/2017.
 */
public interface UserLoginLogRepository extends BaseJpaRepository<UserLoginLog, Long>, UserLoginLogRepositoryCustom {
}
