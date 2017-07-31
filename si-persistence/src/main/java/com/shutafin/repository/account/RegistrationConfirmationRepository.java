package com.shutafin.repository.account;

import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

/**
 * Created by evgeny on 7/10/2017.
 */
public interface RegistrationConfirmationRepository extends PersistentDao<RegistrationConfirmation> {
    RegistrationConfirmation getRegistrationConfirmationByUrlLink(String link);
    Boolean isUserConfirmed(User user);
}
