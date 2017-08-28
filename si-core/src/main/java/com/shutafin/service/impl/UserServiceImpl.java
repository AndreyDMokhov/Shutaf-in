package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void save(UserInfoWeb userInfoWeb) {
        User user = new User();
        user.setFirstName(userInfoWeb.getFirstName());
        user.setLastName(userInfoWeb.getLastName());
        user.setEmail(userInfoWeb.getEmail());

        Long userId = (Long) userRepository.save(user);
        user.setId(userId);
    }

}
