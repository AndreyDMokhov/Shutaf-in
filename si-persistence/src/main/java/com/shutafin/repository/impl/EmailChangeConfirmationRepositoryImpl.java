package com.shutafin.repository.impl;

import com.shutafin.model.entities.EmailChangeConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.repository.EmailChangeConfirmationRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class EmailChangeConfirmationRepositoryImpl extends AbstractEntityDao<EmailChangeConfirmation> implements EmailChangeConfirmationRepository {
    @Override
    public EmailChangeConfirmation getEmailChangeConfirmationByUrlLink(String urlLink, Date currentDate) {
               return (EmailChangeConfirmation)getSession()
                .createQuery("FROM EmailChangeConfirmation e WHERE e.urlLink = :urlLink " +
                                                                        "AND e.isConfirmed = 0 " +
                                                                        "AND e.expiresAt > :currentDate")
                       .setParameter("urlLink", urlLink)
                       .setParameter("currentDate", currentDate)
                       .uniqueResult();
    }

    @Override
    public void deleteAllCurrentEmailChangeRequests(User user) {
        getSession()
                .createQuery("delete from EmailChangeConfirmation ecc where ecc.user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }
}
