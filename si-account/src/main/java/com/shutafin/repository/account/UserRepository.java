package com.shutafin.repository.account;

import com.shutafin.model.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
    List<User> findAllByFirstNameInAndLastNameIn(List<String> firstNames, List<String> lastNames);
}
