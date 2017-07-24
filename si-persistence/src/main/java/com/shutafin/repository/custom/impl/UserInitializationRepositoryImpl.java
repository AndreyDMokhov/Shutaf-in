package com.shutafin.repository.custom.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.entities.UserImage;
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
        UserInit userData = getUserInit(user);
        UserImage userImage = getUserImage(user);
        if (userImage != null) {
            userData.setUserImage(userImage.getImageStorage().getImageEncoded());
            userData.setUserImageId(userImage.getId());
        }
        return userData;
    }

    private UserInit getUserInit(User user) {
        StringBuilder hql = new StringBuilder();
        hql.append("select ");
        hql.append(" new com.shutafin.model.web.user.UserInit ");
        hql.append(" ( ");
        hql.append(" ua.user.firstName, ");
        hql.append(" ua.user.lastName, ");
        hql.append(" ua.language.id ");
        hql.append(" ) ");
        hql.append(" from UserAccount ua ");
        hql.append(" where ua.user.id =:id ");

        return (UserInit) getSession()
                            .createQuery(hql.toString())
                            .setParameter("id", user.getId())
                            .uniqueResult();
    }

    private UserImage getUserImage(User user) {
        StringBuilder hql = new StringBuilder();

        hql.append("select ui ");
        hql.append("FROM UserImage ui, UserAccount ua ");
        hql.append("where ua.userImage.id=ui.id ");
        hql.append(" and ");
        hql.append("ui.user.id = :id ");

        return (UserImage) getSession()
                .createQuery(hql.toString())
                .setParameter("id", user.getId())
                .uniqueResult();
    }
}
