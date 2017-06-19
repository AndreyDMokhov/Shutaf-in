package com.shutafin.service.impl;

import com.shutafin.model.common.User;
import com.shutafin.model.common.UserInfo;
import com.shutafin.model.web.user.UserInfoWeb;
import com.shutafin.repository.GenderRepository;
import com.shutafin.repository.UserInfoRepository;
import com.shutafin.repository.UserRepository;
import com.shutafin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private GenderRepository genderRepository;

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

        UserInfo userInfo = new UserInfo();
        if (userInfoWeb.getAge() != null) {

            userInfo.setAge(userInfoWeb.getAge());
        }

        if (userInfoWeb.getGenderId() != null) {
            userInfo.setGender(genderRepository.findById(userInfoWeb.getGenderId()));
        }


        userInfo.setUser(user);

        userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public void update(UserInfoWeb userInfo) {
        UserInfo dbUserInfo = userInfoRepository.getByUserId(userInfo.getUserId());
        User user = dbUserInfo.getUser();
        user.setEmail(userInfo.getEmail());
        user.setFirstName(userInfo.getFirstName());
        user.setLastName(userInfo.getLastName());

        dbUserInfo.setGender(genderRepository.findById(userInfo.getGenderId()));
        dbUserInfo.setAge(userInfo.getAge());
        userInfoRepository.save(dbUserInfo);
    }

    @Override
    @Transactional
    public UserInfoWeb findByUserId(Long userId) {
        UserInfo userInfo = userInfoRepository.getByUserId(userId);
        if (userInfo == null) {

            return null;
        }

        return getUserInfoWeb(userInfo);

    }

    private UserInfoWeb getUserInfoWeb(UserInfo userInfo) {
        UserInfoWeb userInfoWeb = new UserInfoWeb();
        userInfoWeb.setUserId(userInfo.getUser().getId());
        userInfoWeb.setFirstName(userInfo.getUser().getFirstName());
        userInfoWeb.setLastName(userInfo.getUser().getLastName());
        userInfoWeb.setEmail(userInfo.getUser().getEmail());

        userInfoWeb.setAge(userInfo.getAge());

        if (userInfo.getGender() != null) {
            userInfoWeb.setGenderId(userInfo.getGender().getId());
        }

        return userInfoWeb;
    }

    @Override
    @Transactional
    public List<UserInfoWeb> findAll() {
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        List<UserInfoWeb> userInfoWebList = new ArrayList<>();

        for (UserInfo userInfo : userInfoList) {
            userInfoWebList.add(getUserInfoWeb(userInfo));
        }

        return userInfoWebList;
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


}
