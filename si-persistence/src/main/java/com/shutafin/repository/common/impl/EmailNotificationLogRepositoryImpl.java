package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.EmailNotificationLog;
import com.shutafin.repository.common.EmailNotificationLogRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Edward Kats.
 * 03 / Jul / 2017
 */
@Repository
public class EmailNotificationLogRepositoryImpl extends AbstractEntityDao<EmailNotificationLog> implements EmailNotificationLogRepository {
    @Override
    public List<EmailNotificationLog> getAllFailedEmailNotifications() {
        StringBuilder hql = new StringBuilder(200)
                .append(" SELECT enl ")
                .append(" FROM EmailNotificationLog enl ")
                .append(" WHERE enl.isSendFailed = 1 ");
        return getSession()
                .createQuery(hql.toString())
                .setCacheable(true)
                .getResultList();
    }
}