package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoRequest;
import com.shutafin.repository.account.UserRepository;
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
    public void save(UserInfoRequest userInfoRequest) {
        User user = new User();
        user.setFirstName(userInfoRequest.getFirstName());
        user.setLastName(userInfoRequest.getLastName());
        user.setEmail(userInfoRequest.getEmail());

        Long userId = userRepository.save(user).getId();
        user.setId(userId);
    }

}
