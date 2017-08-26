package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserPersonalInfoWeb;
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
    public void save(UserPersonalInfoWeb userPersonalInfoWeb) {
        User user = new User();
        user.setFirstName(userPersonalInfoWeb.getFirstName());
        user.setLastName(userPersonalInfoWeb.getLastName());
        user.setEmail(userPersonalInfoWeb.getEmail());

        Long userId = (Long) userRepository.save(user);
        user.setId(userId);
    }

    @Override
    @Transactional
    public void update(UserPersonalInfoWeb userInfo) {

    }

    private UserPersonalInfoWeb getUserInfoWeb(User userInfo) {
        UserPersonalInfoWeb userPersonalInfoWeb = new UserPersonalInfoWeb();
        userPersonalInfoWeb.setFirstName(userInfo.getFirstName());
        userPersonalInfoWeb.setLastName(userInfo.getLastName());
        userPersonalInfoWeb.setEmail(userInfo.getEmail());

        return userPersonalInfoWeb;
    }

}
