package com.shutafin.repository.impl;


import com.shutafin.model.entities.User;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends AbstractEntityDao<User> implements UserRepository {

    public User findUserByEmail(String email){
        return (User) getSession()
                .createQuery("SELECT e FROM "+ User.class.getName()+ " e where e.email = :email")
                .setParameter("email", email).uniqueResult();
        }
    }


