package com.shutafin.repository.common;

import com.shutafin.model.entities.EmailImageSource;
import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.List;

@Deprecated
public interface EmailImageSourceRepository extends BaseJpaRepository<EmailImageSource, Long> {
    List<EmailImageSource> findAllByEmailNotificationLog(EmailNotificationLog emailNotificationLog);
}
