package com.shutafin.repository;

import com.shutafin.model.entity.Confirmation;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.Date;

public interface ConfirmationRepository extends BaseJpaRepository<Confirmation, Long> {

    Confirmation findByConfirmationUUIDAndExpiresAtAfterAndIsConfirmedIsFalse(String link, Date date);

}
