package com.shutafin.repository.account;

import com.shutafin.model.entities.ResetPasswordConfirmation;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.Date;

public interface ResetPasswordConfirmationRepository extends BaseJpaRepository<ResetPasswordConfirmation, Long> {

    ResetPasswordConfirmation findByUrlLinkAndExpiresAtBeforeAndIsConfirmedIsTrue(String link, Date date);
}
