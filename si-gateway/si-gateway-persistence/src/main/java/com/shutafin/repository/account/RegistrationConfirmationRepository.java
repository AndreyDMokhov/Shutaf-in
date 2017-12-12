package com.shutafin.repository.account;

import com.shutafin.model.entities.RegistrationConfirmation;
import com.shutafin.repository.base.BaseJpaRepository;

@Deprecated
public interface RegistrationConfirmationRepository extends BaseJpaRepository<RegistrationConfirmation, Long> {
    RegistrationConfirmation findByUrlLink(String link);

}
