package com.shutafin.core.service.impl;

import com.shutafin.core.service.UserService;
import com.shutafin.model.entities.User;
import com.shutafin.model.web.account.AccountUserInfoRequest;
import com.shutafin.model.web.account.AccountUserWeb;
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
    public void save(AccountUserInfoRequest userInfoRequest) {
        User user = new User();
        user.setFirstName(userInfoRequest.getFirstName());
        user.setLastName(userInfoRequest.getLastName());
        user.setEmail(userInfoRequest.getEmail());

        userRepository.save(user);
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public AccountUserWeb getAccountUserWebById(Long userId) {
        User user = findUserById(userId);
        return new AccountUserWeb(
                user.getId(),
                user.getLastName(),
                user.getFirstName());
    }
}
