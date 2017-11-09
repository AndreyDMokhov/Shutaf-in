package com.shutafin.repository.account;

import com.shutafin.model.entities.EmailChangeConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;

import java.util.Date;


public interface EmailChangeConfirmationRepository extends BaseJpaRepository<EmailChangeConfirmation, Long> {

    EmailChangeConfirmation findByUrlLinkAndExpiresAtBefore(String link, Date currentDate);
    void deleteAllByUser(User user);


}
