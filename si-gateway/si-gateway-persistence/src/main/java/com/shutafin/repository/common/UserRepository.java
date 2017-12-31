package com.shutafin.repository.common;


import com.shutafin.model.entities.User;
import com.shutafin.repository.base.BaseJpaRepository;

@Deprecated
public interface UserRepository extends BaseJpaRepository<User, Long> {
    User findByEmail(String email);
}
