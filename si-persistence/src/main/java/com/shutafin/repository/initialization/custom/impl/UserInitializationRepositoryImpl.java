package com.shutafin.repository.initialization.custom.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInitializationData;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.initialization.custom.UserInitializationRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 03.07.2017.
 */
@Repository
public class UserInitializationRepositoryImpl extends AbstractEntityDao implements UserInitializationRepository {

    @Override
    public UserInitializationData getUserInitializationData(User user) {
        StringBuilder hql = new StringBuilder();
        hql.append("select ");
        hql.append(" new com.shutafin.model.web.user.UserInitializationData ");
        hql.append(" ( ");
        hql.append(" ua.user.firstName, ");
        hql.append(" ua.user.lastName, ");
        hql.append(" l.id as languageId");
        hql.append(" ) ");
        hql.append(" from UserAccount ua, Language l ");
        hql.append(" where ");
        hql.append(" ua.user.id =:id ");
        hql.append(" and ");
        hql.append(" l.id = ua.language ");

        return (UserInitializationData) getSession()
                .createQuery(hql.toString())
                .setParameter("id", user.getId())
                .uniqueResult();
    }

}
