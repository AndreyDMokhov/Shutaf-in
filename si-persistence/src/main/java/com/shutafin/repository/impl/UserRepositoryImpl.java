package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository
public class UserRepositoryImpl extends AbstractEntityDao<User> implements UserRepository {


    @Override
    public User findByUserEmail(String email) {
        try {
            return  (User) getSession()
                    .createQuery("from " + getEntityClass().getName() + " where email = :email")
                    .setParameter("email", email)
                    .getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }
}
