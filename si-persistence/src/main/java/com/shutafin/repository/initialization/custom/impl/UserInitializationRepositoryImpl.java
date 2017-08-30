package com.shutafin.repository.initialization.custom.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponse;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.initialization.custom.UserInitializationRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 03.07.2017.
 */
@Repository
public class UserInitializationRepositoryImpl extends AbstractEntityDao implements UserInitializationRepository {

    @Override
    public UserInfoResponse getUserInitializationData(User user) {
        StringBuilder hql = new StringBuilder()
                .append("select ")
                .append(" new com.shutafin.model.web.user.UserInfoResponse ")
                .append(" ( ")
                .append(" ua.user.firstName, ")
                .append(" ua.user.lastName, ")
                .append(" ua.user.email, ")
                .append(" l.id as languageId")
                .append(" ) ")
                .append(" from UserAccount ua, Language l ")
                .append(" where ")
                .append(" ua.user.id =:id ")
                .append(" and ")
                .append(" l.id = ua.language ");

        return (UserInfoResponse) getSession()
                .createQuery(hql.toString())
                .setParameter("id", user.getId())
                .uniqueResult();
    }

}
