package com.shutafin.service.impl;

import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.repository.RegistrationConfirmationRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.service.RegistrationConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by evgeny on 7/10/2017.
 */
@Service
@Transactional
public class RegistrationConfirmationServiceImpl extends AbstractEntityDao<RegistrationConfirmation> implements RegistrationConfirmationService {
    public Boolean isUserConfirmed(User user){
        return (Boolean) getSession()
                .createQuery("SELECT u.isConfirmed FROM RegistrationConfirmation u WHERE u.user = :user")
                .setParameter("user", user)
                .getSingleResult();
    }
}
