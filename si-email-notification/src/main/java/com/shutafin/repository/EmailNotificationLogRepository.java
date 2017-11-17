package com.shutafin.repository;

import com.shutafin.entity.EmailNotificationLog;
import com.shutafin.repository.base.BaseJpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationLogRepository extends BaseJpaRepository<EmailNotificationLog, Long> {

}
