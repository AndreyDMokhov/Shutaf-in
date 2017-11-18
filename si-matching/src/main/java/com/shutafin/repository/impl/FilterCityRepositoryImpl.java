package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.City;
import com.shutafin.repository.common.FilterCityRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FilterCityRepositoryImpl implements FilterCityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<City> getUserFilterCity(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT fc.city ")
                .append(" FROM FilterCity fc ")
                .append(" WHERE fc.user = :user ");
        return entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .getResultList();
    }

    @Override
    public List<User> getAllMatchedUsers(User user, List<User> matchedUsers) {
        if (getUserFilterCity(user).isEmpty() || matchedUsers.isEmpty()){
            return matchedUsers;
        }
        StringBuilder hql = new StringBuilder()
                .append(" SELECT distinct fc2.user ")
                .append(" FROM FilterCity fc1, FilterCity fc2")
                .append(" WHERE fc2.city.id = fc1.city.id")
                .append(" AND  fc1.user = :user ")
                .append(" AND fc2.user IN (:matchedUsers) ");

        return entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .setParameter("matchedUsers", matchedUsers)
                .getResultList();
    }

    @Override
    public void deleteUserFilterCity(User user) {
        StringBuilder hql = new StringBuilder().append(" DELETE FROM FilterCity fc where fc.user = :user ");
        entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .executeUpdate();
    }
}
