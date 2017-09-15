package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.match.UserExamKey;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 9/5/2017.
 */
public interface UserExamKeyRepository extends PersistentDao<UserExamKey> {
    void delete(User user);
    UserExamKey getUserExamKey(User user);
    List<User> getMatchedUsers(List<String> keys);
}
