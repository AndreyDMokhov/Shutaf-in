package com.company.repository.impl;

import com.company.model.User;
import com.company.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }



    @Override
    public void save(User user) {

        getSession().save(user);

    }

    @Override
    public void update(User user) {
        User load = getSession().load(User.class, user.getUserId());
        load.setFirstName(user.getFirstName());
        load.setLastName(user.getLastName());
        getSession().update(load);
    }

    @Override
    public User findByUserId(Integer userId) {
        return getSession().get(User.class, userId);
    }

    @Override
    public List<User> findAll() {
        StringBuilder hql = new StringBuilder();
        hql.append("from User");
        Query query = getSession().createQuery(hql.toString());
        return query.getResultList();
    }
}
