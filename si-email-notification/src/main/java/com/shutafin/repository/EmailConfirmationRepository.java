package com.shutafin.repository;

import com.shutafin.model.entity.EmailConfirmation;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.Date;

public interface EmailConfirmationRepository extends BaseJpaRepository<EmailConfirmation, Long> {

    EmailConfirmation findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(String link, Date date);

}
