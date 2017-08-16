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
    public List<User> findUsersByFirstAndLastName(String firstName, String lastName) {
        return getSession()
                .createQuery("SELECT u from " + getEntityClass().getName() +
                        " u WHERE (u.firstName =:firstName AND u.lastName =:lastName)" +
                        " OR (u.firstName =:lastName AND u.lastName =:firstName) order by u.createdDate")
                .setParameter("firstName", firstName)
                .setParameter("lastName", lastName)
                .list();
    }
    @Override
    public List<User> findUsersByName(String name) {
        return getSession()
                .createQuery("SELECT u from " + getEntityClass().getName() +
                        " u WHERE u.firstName =:name OR u.lastName =:name) order by u.createdDate")
                .setParameter("name", name)
                .list();
    }
}