package com.shutafin.repository.initialization;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoResponseDTO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by Alex on 03.07.2017.
 */
@Repository
public class UserInitializationRepositoryImpl implements UserInitializationRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

        try {
            return (UserInfoResponseDTO) entityManager
                    .createQuery(hql.toString())
                    .setParameter("id", user.getId())
                    .getSingleResult();
        } catch (NoResultException ignore) {
            return null;
        }
    }

}
