package com.shutafin.repository;

import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationLogRepository extends BaseJpaRepository<EmailNotificationLog, Long> {

}
