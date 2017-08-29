package com.shutafin.service.impl;

import com.shutafin.model.entities.User;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.repository.common.UserRepository;
import com.shutafin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void save(UserInfoWeb userInfoWeb) {
        User user = new User();
        user.setId(userInfoWeb.getUserId());
        user.setFirstName(userInfoWeb.getFirstName());
        user.setLastName(userInfoWeb.getLastName());
        user.setEmail(userInfoWeb.getEmail());

        Long userId = (Long) userRepository.save(user);
        user.setId(userId);
    }

    @Override
    @Transactional
    public List<UserInfoWeb> findAll() {
        List<User> userInfoList = userRepository.findAll();
        List<UserInfoWeb> userInfoWebList = new ArrayList<>();

        for (User user : userInfoList) {
            userInfoWebList.add(getUserInfoWeb(user));
        }

        return userInfoWebList;
    }

    private UserInfoWeb getUserInfoWeb(User userInfo) {
        UserInfoWeb userInfoWeb = new UserInfoWeb();
        userInfoWeb.setUserId(userInfo.getId());
        userInfoWeb.setFirstName(userInfo.getFirstName());
        userInfoWeb.setLastName(userInfo.getLastName());

        return userInfoWeb;
    }

}
