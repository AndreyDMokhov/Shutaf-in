package com.shutafin.repository.account;

import com.shutafin.model.entities.ResetPasswordConfirmation;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.Date;

@Deprecated
public interface ResetPasswordConfirmationRepository extends BaseJpaRepository<ResetPasswordConfirmation, Long> {

    ResetPasswordConfirmation findByUrlLinkAndExpiresAtAfterAndIsConfirmedIsFalse(String link, Date date);

}
