package com.shutafin.repository.custom.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInit;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.custom.UserInitializationRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 03.07.2017.
 */
@Repository
public class UserInitializationRepositoryImpl extends AbstractEntityDao implements UserInitializationRepository {

    @Override
    public UserInit getUserInitializationData(User user) {
        StringBuilder hql = new StringBuilder();
        hql.append("select new com.shutafin.model.web.user.UserInit ");
        hql.append(" ( ");
        hql.append(" ua.user.firstName, ");
        hql.append(" ua.user.lastName, ");
        hql.append(" ua.language.id ");
        hql.append(" ) ");
        hql.append(" from UserAccount ua ");
        hql.append(" where ua.id =:id ");

        return (UserInit) getSession()
                            .createQuery(hql.toString())
                            .setParameter("id", user.getId())
                            .uniqueResult();
    }
}
