package com.shutafin.repository;

import com.shutafin.model.entities.EmailChangeConfirmation;
import com.shutafin.model.entities.User;
import com.shutafin.repository.base.PersistentDao;

import java.util.Date;


public interface EmailChangeConfirmationRepository extends PersistentDao<EmailChangeConfirmation> {

    EmailChangeConfirmation getEmailChangeConfirmationByUrlLink(String urlLink, Date currentDate);
    void deleteAllCurrentEmailChangeRequests(User user);

}
