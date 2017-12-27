package com.shutafin.repository;

import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;

public interface EmailNotificationLogRepository extends BaseJpaRepository<EmailNotificationLog, Long> {

    List<EmailNotificationLog> findAllByIsSendFailedTrue();

}
