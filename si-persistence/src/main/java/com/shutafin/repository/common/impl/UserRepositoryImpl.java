package com.shutafin.repository.common.impl;


import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class UserRepositoryImpl extends AbstractEntityDao<User> implements UserRepository {

    public User findUserByEmail(String email) {
        return (User) getSession()
                .createQuery("SELECT e FROM User e where e.email = :email")
                .setParameter("email", email)
                .setCacheable(true)
                .uniqueResult();
        }

    @Override
    public Boolean isEmailExists(String email) {
        Long results =  (Long) getSession()
                .createQuery("select count(u.email) from User u where u.email =:email")
                .setParameter("email", email)
                .uniqueResult();
        return results > 0;
    }

    @Override
    public List<User> findAllUsers() {
        return getSession()
                .createQuery("SELECT u FROM User")
                .list();
    }

    @Override
    public List<User> findUsersByFirstAndLastName(List<String> names) {
        return getSession()
                .createQuery("SELECT u FROM User u " +
                        " WHERE u.firstName in (:names) " +
                        " AND u.lastName in (:names) " +
                        " order by u.createdDate")
                .setParameter("names", names)
                .list();
    }
}