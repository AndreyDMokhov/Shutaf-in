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
        StringBuilder hql = new StringBuilder()
        .append("select ")
        .append(" new com.shutafin.model.web.user.UserInitializationData ")
        .append(" ( ")
        .append(" ua.user.firstName, ")
        .append(" ua.user.lastName, ")
        .append(" l.id as languageId")
        .append(" ) ")
        .append(" from UserAccount ua, Language l ")
        .append(" where ")
        .append(" ua.user.id =:id ")
        .append(" and ")
        .append(" l.id = ua.language ");

        return (UserInitializationData) getSession()
                .createQuery(hql.toString())
                .setParameter("id", user.getId())
                .uniqueResult();
    }

}
