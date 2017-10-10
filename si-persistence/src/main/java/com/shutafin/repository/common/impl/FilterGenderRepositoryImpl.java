package com.shutafin.repository.common.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.FilterGender;
import com.shutafin.model.entities.infrastructure.Gender;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.common.FilterGenderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by evgeny on 9/23/2017.
 */
@Repository
public class FilterGenderRepositoryImpl extends AbstractEntityDao<FilterGender> implements FilterGenderRepository {
    @Override
    public List<Gender> getUserFilterGender(User user) {
        StringBuilder hql = new StringBuilder()
                .append(" SELECT fg.gender ")
                .append(" FROM FilterGender fg")
                .append(" WHERE fg.user = :user ");
        return getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .list();
    }

    @Override
    public List<User> getAllMatchedUsers(User user, List<User> matchedUsers) {
        if (getUserFilterGender(user).isEmpty() || matchedUsers.isEmpty()){
            return matchedUsers;
        }
        StringBuilder hql = new StringBuilder()
                .append(" SELECT ui.user ")
                .append(" FROM FilterGender fg INNER JOIN UserInfo ui")
                .append(" ON fg.gender.id = ui.gender.id")
                .append(" WHERE fg.user = :user ")
                .append(" AND ui.user IN (:matchedUsers) ");

        return getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .setParameter("matchedUsers", matchedUsers)
                .list();
    }

    @Override
    public void deleteUserFilterGender(User user) {
        StringBuilder hql = new StringBuilder().append(" DELETE FROM FilterGender fg WHERE fg.user = :user ");
        getSession()
                .createQuery(hql.toString())
                .setParameter("user", user)
                .executeUpdate();
    }
}
