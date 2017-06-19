package com.shutafin.repository.impl;

import com.shutafin.model.entities.User;
import com.shutafin.repository.UserRepository;
import com.shutafin.repository.base.AbstractEntityDao;
import org.springframework.stereotype.Repository;


@Repository
public class UserRepositoryImpl extends AbstractEntityDao<User> implements UserRepository {
}
