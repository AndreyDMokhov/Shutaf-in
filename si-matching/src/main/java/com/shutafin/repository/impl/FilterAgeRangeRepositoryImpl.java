package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.repository.common.FilterAgeRangeRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FilterAgeRangeRepositoryImpl implements FilterAgeRangeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public AgeRangeWebDTO getUserFilterAgeRange(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT new com.shutafin.model.web.user.AgeRangeWebDTO(far.fromAge, far.toAge) ")
                .append(" FROM FilterAgeRange far ")
                .append(" WHERE far.user = :user ");
        try {
            return (AgeRangeWebDTO) entityManager
                    .createQuery(hql.toString())
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<User> getAllMatchedUsers(User user, List<User> matchedUsers) {
        AgeRangeWebDTO ageRangeWebDTO = getUserFilterAgeRange(user);
        if (ageRangeWebDTO == null || matchedUsers.isEmpty()){
            return matchedUsers;
        }

        StringBuilder hql = new StringBuilder()
                .append(" SELECT ui.user ")
                .append(" FROM UserInfo ui ")
                .append(" WHERE ui.user IN (:matchedUsers) ")
                .append(" AND TIMESTAMPDIFF(YEAR, ui.dateOfBirth, CURDATE()) BETWEEN :fromAge AND :toAge ")
                .append("  ");

        return entityManager
                .createQuery(hql.toString())
                .setParameter("matchedUsers", matchedUsers)
                .setParameter("fromAge", ageRangeWebDTO.getFromAge())
                .setParameter("toAge", ageRangeWebDTO.getToAge())
                .getResultList();
    }

}
