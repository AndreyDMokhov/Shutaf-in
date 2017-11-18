package com.shutafin.repository;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.City;

import java.util.List;

public interface FilterCityRepositoryCustom {
    List<City> getUserFilterCity(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterCity(User user);
}
