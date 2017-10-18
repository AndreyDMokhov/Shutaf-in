package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.FilterAgeRange;
import com.shutafin.model.web.user.AgeRangeWebDTO;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.FilterAgeRangeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 10/1/2017.
 */
@Repository
public class FilterAgeRangeRepositoryImpl extends AbstractEntityDao<FilterAgeRange> implements FilterAgeRangeRepository {
    @Override
    public AgeRangeWebDTO getUserFilterAgeRange(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT new com.shutafin.model.web.user.AgeRangeWebDTO(far.fromAge, far.toAge) ")
                .append(" FROM FilterAgeRange far ")
                .append(" WHERE far.user = :user ");
        return (AgeRangeWebDTO) getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .uniqueResult();
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

        return getSession()
                .createQuery(hql.toString())
                .setParameter("matchedUsers", matchedUsers)
                .setParameter("fromAge", ageRangeWebDTO.getFromAge())
                .setParameter("toAge", ageRangeWebDTO.getToAge())
                .list();
    }

    @Override
    public void deleteUserFilterAgeRange(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" DELETE FROM FilterAgeRange far where far.user = :user ");
        getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .executeUpdate();
    }
}
