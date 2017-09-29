package com.shutafin.repository.initialization.custom.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import com.shutafin.repository.base.AbstractEntityDao;
import com.shutafin.repository.initialization.custom.UserInitializationRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Alex on 03.07.2017.
 */
@Repository
public class UserInitializationRepositoryImpl extends AbstractEntityDao implements UserInitializationRepository {

    @Override
    public UserInfoResponseDTO getUserInitializationData(User user) {
        StringBuilder hql = new StringBuilder()
                .append("select ")
                .append(" new com.shutafin.model.web.user.UserInfoResponseDTO ")
                .append(" ( ")
                .append(" u.id as userId, ")
                .append(" u.firstName, ")
                .append(" u.lastName, ")
                .append(" u.email, ")
                .append(" ua.language.id as languageId, ")
                .append(" c.id as countryId, ")
                .append(" ci.id as cityId, ")
                .append(" g.id as genderId, ")
                .append(" ui.dateOfBirth, ")
                .append(" ui.facebookLink, ")
                .append(" ui.profession, ")
                .append(" ui.company, ")
                .append(" ui.phoneNumber ")
                .append(" ) ")
                .append(" from User u,  ")
                .append(" UserAccount ua, ")
                .append(" UserInfo ui ")
                .append(" left join ui.gender g ")
                .append(" left join ui.currentCity ci ")
                .append(" left join ui.currentCity.country c ")
                .append(" where ")
                .append(" ua.user = u ")
                .append(" and ")
                .append(" ui.user = u ")
                .append(" and ")
                .append(" u.id = :id");

        return (UserInfoResponseDTO) getSession()
                .createQuery(hql.toString())
                .setParameter("id", user.getId())
                .uniqueResult();
    }

}
