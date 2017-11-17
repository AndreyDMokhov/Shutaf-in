package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.repository.common.FilterGenderRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FilterGenderRepositoryImpl implements FilterGenderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;


    
    public Gender getUserFilterGender(User user) {

        StringBuilder hql = new StringBuilder()
                .append(" SELECT fg.gender ")
                .append(" FROM FilterGender fg")
                .append(" WHERE fg.user = :user ");


        try {
            return (Gender) entityManager
                    .createQuery(hql.toString())
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getAllMatchedUsers(User user, List<User> matchedUsers) {
        if (getUserFilterGender(user) == null || matchedUsers.isEmpty()){
            return matchedUsers;
        }
        StringBuilder hql = new StringBuilder()
                .append(" SELECT ui.user ")
                .append(" FROM FilterGender fg, UserInfo ui")
                .append(" WHERE fg.gender.id = ui.gender.id")
                .append(" AND fg.user = :user ")
                .append(" AND ui.user IN (:matchedUsers) ");

        return entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .setParameter("matchedUsers", matchedUsers)
                .getResultList();
    }

    @Override
    public void deleteUserFilterGender(User user) {
        StringBuilder hql = new StringBuilder().append(" DELETE FROM FilterGender fg WHERE fg.user = :user ");
        entityManager
                .createQuery(hql.toString())
                .setParameter("user", user)
                .executeUpdate();
    }
}
