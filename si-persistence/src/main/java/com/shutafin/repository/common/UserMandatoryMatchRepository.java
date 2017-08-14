package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserMandatoryMatchResult;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 8/10/2017.
 */
public interface UserMandatoryMatchRepository extends PersistentDao<UserMandatoryMatchResult> {
    List<User> findPartners(User user);
}
