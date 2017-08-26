package com.shutafin.repository.account.impl;

import com.shutafin.model.entities.ResetPasswordConfirmation;
import com.shutafin.repository.account.ResetPasswordConfirmationRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public class ResetPasswordConfirmationRepositoryImpl extends AbstractEntityDao<ResetPasswordConfirmation>
        implements ResetPasswordConfirmationRepository {

    @Override
    public ResetPasswordConfirmation findValidUrlLink(String urlLink) {
        return (ResetPasswordConfirmation) getSession()
                .createQuery("SELECT e FROM ResetPasswordConfirmation e WHERE e.urlLink = :urlLink " +
                        "AND e.isConfirmed = false AND e.expiresAt > :date")
                .setParameter("urlLink", urlLink)
                .setParameter("date", new Date())
                .uniqueResult();
    }
}
