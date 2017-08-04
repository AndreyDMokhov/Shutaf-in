package com.shutafin.repository.common.impl;


import com.shutafin.model.entities.User;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends AbstractEntityDao<User> implements UserRepository {

    public User findUserByEmail(String email) {
        return (User) getSession()
                .createQuery("SELECT e FROM User e where e.email = :email")
                .setParameter("email", email)
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
}