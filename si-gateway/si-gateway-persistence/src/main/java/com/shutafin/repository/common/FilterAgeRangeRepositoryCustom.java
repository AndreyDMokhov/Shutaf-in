package com.shutafin.repository.common;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;

import java.util.List;

@Deprecated
public interface FilterAgeRangeRepositoryCustom {
    AgeRangeWebDTO getUserFilterAgeRange(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
}
