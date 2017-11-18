package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;

import java.util.List;

public interface FilterAgeRangeRepositoryCustom {
    AgeRangeWebDTO getUserFilterAgeRange(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
}
