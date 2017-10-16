package com.shutafin.repository.common;

import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
public interface EmailNotificationLogRepository extends PersistentDao<EmailNotificationLog> {
    List<EmailNotificationLog> getAllFailedEmailNotifications();
}
