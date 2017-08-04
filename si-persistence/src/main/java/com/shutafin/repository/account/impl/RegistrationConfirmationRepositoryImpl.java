package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.repository.account.RegistrationConfirmationRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

/**
 * Created by evgeny on 7/10/2017.
 */
@Repository
public class RegistrationConfirmationRepositoryImpl  extends AbstractEntityDao<RegistrationConfirmation> implements RegistrationConfirmationRepository {
    @Override
    public RegistrationConfirmation getRegistrationConfirmationByUrlLink(String link) {
        return (RegistrationConfirmation) getSession()
                .createQuery("FROM RegistrationConfirmation u WHERE u.urlLink = :link AND u.isConfirmed = 0")
                .setParameter("link", link)
                .uniqueResult();
    }

    @Override
    public Boolean isUserConfirmed(User user) {
        return (Boolean) getSession()
                .createQuery("SELECT u.isConfirmed FROM RegistrationConfirmation u WHERE u.user = :user")
                .setParameter("user", user)
                .getSingleResult();
    }
}
