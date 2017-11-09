package com.shutafin.internal.repository;

import com.shutafin.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepositoryInternal extends JpaRepository<User, Long> {
}
