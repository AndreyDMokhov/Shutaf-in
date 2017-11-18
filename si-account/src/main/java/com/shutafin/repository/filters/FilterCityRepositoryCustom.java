package com.shutafin.repository.filters;

import com.shutafin.model.entities.User;
import com.shutafin.model.infrastructure.City;

import java.util.List;

public interface FilterCityRepositoryCustom {
    List<City> getUserFilterCity(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterCity(User user);
}
