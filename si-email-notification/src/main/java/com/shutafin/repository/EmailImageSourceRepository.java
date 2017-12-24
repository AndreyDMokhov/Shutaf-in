package com.shutafin.repository;

import com.shutafin.model.entity.EmailImageSource;
import com.shutafin.model.entity.EmailNotificationLog;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;

public interface EmailImageSourceRepository extends BaseJpaRepository<EmailImageSource, Long> {

    List<EmailImageSource> findAllByEmailNotificationLog(EmailNotificationLog emailNotificationLog);

}
