package com.shutafin.repository;

import com.shutafin.entity.EmailNotificationLog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailNotificationLogRepository extends CrudRepository<EmailNotificationLog, Long> {

}
