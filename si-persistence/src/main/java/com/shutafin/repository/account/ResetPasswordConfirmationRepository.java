package com.shutafin.repository.account;

import com.shutafin.model.entities.ResetPasswordConfirmation;
import com.shutafin.repository.base.PersistentDao;

public interface ResetPasswordConfirmationRepository extends PersistentDao<ResetPasswordConfirmation> {

    ResetPasswordConfirmation findValidUrlLink(String urlLink);
}
