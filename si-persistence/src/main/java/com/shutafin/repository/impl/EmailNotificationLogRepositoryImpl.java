package com.shutafin.repository.impl;

import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.repository.EmailNotificationLogRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Repository
public class EmailNotificationLogRepositoryImpl extends AbstractEntityDao<EmailNotificationLog> implements EmailNotificationLogRepository {
}