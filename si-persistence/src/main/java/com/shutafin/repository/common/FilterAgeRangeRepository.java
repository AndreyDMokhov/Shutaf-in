package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;

/**
 * Created by evgeny on 10/1/2017.
 */
public interface FilterAgeRangeRepository extends PersistentDao<FilterAgeRange> {
    AgeRangeWebDTO getUserFilterAgeRange(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterAgeRange(User user);

}
