package com.shutafin.repository.account;

import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.repository.base.BaseJpaRepository;

/**
 * Created by evgeny on 7/10/2017.
 */
public interface RegistrationConfirmationRepository extends BaseJpaRepository<RegistrationConfirmation, Long> {
    RegistrationConfirmation findByUrlLink(String link);

}
