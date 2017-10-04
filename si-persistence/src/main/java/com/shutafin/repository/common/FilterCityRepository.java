package com.shutafin.repository.common;

import com.shutafin.model.entities.FilterCity;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.model.entities.infrastructure.Question;
import com.shutafin.repository.base.PersistentDao;

import java.util.List;
/**
 * Created by evgeny on 9/13/2017.
 */
public interface FilterCityRepository extends PersistentDao<FilterCity> {
    List<City> getUserFilterCity(User user);
    List<User> getAllMatchedUsers(User user, List<User> matchedUsers);
    void deleteUserFilterCity(User user);
}
