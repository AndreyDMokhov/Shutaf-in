package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.FilterCity;
import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.FilterCityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 9/13/2017.
 */
@Repository
public class FilterCityRepositoryImpl extends AbstractEntityDao<FilterCity> implements FilterCityRepository {
    @Override
    public List<City> getUserFilterCity(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT fc.city ")
                .append(" FROM FilterCity fc ")
                .append(" WHERE fc.user = :user ");
        return getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .list();
    }

    @Override
    public List<User> getAllMatchedUsers(User user, List<User> matchedUsers) {
        if (getUserFilterCity(user).isEmpty() || matchedUsers.isEmpty()){
            return matchedUsers;
        }
        StringBuilder hql = new StringBuilder()
                .append(" SELECT distinct fc2.user ")
                .append(" FROM FilterCity fc1 LEFT JOIN FilterCity fc2")
                .append(" ON fc2.city.id = fc1.city.id")
                .append(" WHERE fc1.user = :user ")
                .append(" AND fc2.user IN (:matchedUsers) ");

        return getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .setParameter("matchedUsers", matchedUsers)
                .list();
    }

    @Override
    public void deleteUserFilterCity(User user) {
        StringBuilder hql = new StringBuilder().append(" DELETE FROM FilterCity fc where fc.user = :user ");
        getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .executeUpdate();
    }
}
